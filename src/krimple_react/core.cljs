(ns krimple-react.core
  (:require
   [cljs.test :refer-macros [is async]]
   [goog.dom :as gdom]
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]
   [om.core]
   [datascript.core :as d]
   [devcards-om-next.core :refer-macros [defcard-om-next om-next-root]]
   [krimple-react.containers.media-player-app-container
    :refer [MediaPlayerAppContainer media-player-app-container]]
   [krimple-react.containers.media-player-container :refer [media-player-container]]
   [krimple-react.components.media-player-app-component
    :refer [MediaPlayerAppComponent media-player-app-component]]
   [krimple-react.components.media-item-component :as mic
    :refer [MediaItemComponent media-item-component]]
   [krimple-react.components.media-list-component :as mlist
    :refer [MediaListComponent media-list-component]]
   #_[om.core :as om :include-macros true]
   [sablono.core :as sab :include-macros true])
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest]]))

(enable-console-print!)

(def app-state
  (atom
   {:app/title "Chariot Video Stream (Om.Next)"
    :video/current-id "player-place"
    :video/selected-video "43418419"
    :video/items {"185336500" {:item/id "185336500"
                               :item/title "Van Neumann Machine"
                               :item/description "Something about VNMs"}
                  "43418419" {:item/id "43418419"
                              :item/title "Effective Scala"
                              :item/description "Points, sharp edges, rough patches"}}}))

(defmulti read (fn [env key params] key))
(defmethod read :default
  [{:keys [state] :as env} key params]
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value}
      {:value :not-found})))
(defmulti mutate om/dispatch)
(defmethod mutate :default
  [{:keys [state] :as env} key params]
  [key params]
  #_(println "Pointlessly called mutate with key '" key "'"))
(defmethod mutate 'do/select-video!
  [{:keys [state] :as env} key {:keys [selected-video] :as params}]
  {:action #(swap! state assoc :video/selected-video selected-video)
   :value {:keys [:video/selected-video]}})

(def my-parser
  "Break parser out so I can see it"
  (om/parser {:read read :mutate mutate}))

(defonce reconciler
  (om/reconciler
   {:state app-state
    :parser my-parser}))

(defn read-media-item
  [state id]
  )


(defcard first-card
  (sab/html [:div
             [:h1 "This is your first devcard!"]]))

(defcard-om-next app-container
  "Test that the MediaPlayerAppContainer is visible"
  MediaPlayerAppContainer
  reconciler
  {:inspect-data true :history true})

#_(defcard-om-next media-item
  "Test that the Media Item Component makes sense"
  MediaItemComponent
  (om/reconciler {:state {:item/id "12345"
                          :item/title "Media Item Component title"
                          :item/description "Description of media item"}
                  :parser my-parser})
  {:inspect-data true :history true})

#_(defcard-om-next media-list
  "Test the Media List Component"
  MediaListComponent
  reconciler
  {:inspect-data true :history true})

(defn main []
  ;; conditionally start the app based on whether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (om/add-root! reconciler MediaPlayerAppContainer node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html

