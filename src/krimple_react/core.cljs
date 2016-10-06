(ns krimple-react.core
  "Single-Page Application to display one of *two* Vimeo videos.

  This was initially derived from a skeletal [React
  presentation](https://github.com/krimple/libertyjs-react-talk-public)
  for the [LibertyJS User Group](http://www.libertyjs.com/)."
  (:require
   [cljs.test :refer-macros [is async]]
   [datascript.core :as d]
   [devcards-om-next.core :refer-macros [defcard-om-next om-next-root]]
   [goog.dom :as gdom]
   [krimple-react.components.media-item-component :as mic
    :refer [MediaItemComponent media-item-component]]
   [krimple-react.components.media-list-component :as mlist
    :refer [MediaListComponent media-list-component]]
   [krimple-react.components.media-player-app-component
    :refer [MediaPlayerAppComponent media-player-app-component]]
   [krimple-react.containers.media-player-app-container
    :refer [MediaPlayerAppContainer media-player-app-container]]
   [krimple-react.containers.media-player-container :refer [media-player-container]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]
   [sablono.core :as sab :include-macros true])
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest]]))

;;; Allow us to print to the Console
(enable-console-print!)

(def videos
  "List of available videos (hard-coded at the moment)"
  [{:item/id "185336500"
    :item/title "Van Neumann Machine"
    :item/description "Something about VNMs"}
   {:item/id "43418419"
    :item/title "Effective Scala"
    :item/description "Points, sharp edges, rough patches"}])

(defn map-using
  "Create a map from a collection, using f to generate the key.

  There's probably a built-in, but I can't think of it at the moment."
  [coll f]
  (let [use-f (fn [accum item]
                (let [k (f item)]
                  (assoc accum k item)))]
    (reduce use-f {} coll)))

(def app-state
  "All application state:

  - A title
  - A place to put the *Vimeo Player*
  - The ID of the video to show first
  - A \"database\" of videos"
  (atom
   {:app/title "Chariot Video Stream (Om.Next)"
    :video/current-id "player-place"
    :video/selected-video "43418419"
    :video/items (map-using videos :item/id)}))

(defmulti read
  "Return data from the application state, depending on the key given.
  By default, do a simple lookup, but if need be we can write methods
  for arbitarily complex queries."
  (fn [env key params] key))

;; Look up data by key by default.
;;
;; Return `:not-found` in case it isn't found!
(defmethod read :default
  [{:keys [state] :as env} key params]
  (let [st @state]
    (if-let [[_ value] (find st key)]
      {:value value}
      {:value :not-found})))

(defmulti mutate
  "Update application data: keys will be symbols. (Conventionally:
  keywords are used for `read` operations, to look like structure
  accesses, and symbols are used for `mutate` operations"
  om/dispatch)

;; Update the `:video/selected-video` value using the
;; `:selected-video` parameter
(defmethod mutate 'do/select-video!
  [{:keys [state] :as env} key {:keys [selected-video] :as params}]
  {:action #(swap! state assoc :video/selected-video selected-video)
   :value {:keys [:video/selected-video]}})

(def my-parser
  "Break parser out so I can see it (for development)"
  (om/parser {:read read :mutate mutate}))

;; The Om Reconciler ties state and the parser together
(defonce reconciler
  (om/reconciler
   {:state app-state
    :parser my-parser}))

;; Development card
(defcard first-card
  "Yes, a dev card"
  (sab/html [:div
             [:h1 "This is your first devcard!"]]))

(defcard-om-next app-container
  "Test that the MediaPlayerAppContainer is visible"
  MediaPlayerAppContainer
  reconciler
  {:inspect-data true :history true})


(defn main
  "conditionally start the app based on whether the #main-app-area
  node is on the page (it won't be on a devcard page...)"
  []
  (if-let [node (.getElementById js/document "main-app-area")]
    (om/add-root! reconciler MediaPlayerAppContainer node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html

