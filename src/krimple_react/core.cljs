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
   [krimple-react.media-player-app
    :refer [MediaPlayerApp media-player-app]]
   [krimple-react.media-item :refer (MediaItem, media-item)]
   [krimple-react.media-list :refer (MediaList media-list)]
   [krimple-react.media-player :refer (MediaPlayer media-player)]
   [krimple-react.media-player-app :refer (MediaPlayerApp media-player-app)]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]
   [sablono.core :as sab :include-macros true])
  (:require-macros
   [devcards.core :as dc :refer [defcard defcard-doc
                                 #_defcard-om-next deftest]]))

;;; Allow us to print to the Console
(enable-console-print!)

(def videos
  "List of available videos (hard-coded at the moment)"
  [{:id "185336500"
    :title "Van Neumann Machine"
    :description "Something about VNMs"}
   {:id "184394491"
    :title "Why does Functional Programming Even Matter?"
    :description "Sujan Kapadia"}
   {:id "184068444"
    :title "Sea Child"
    :description "Random video"}
   {:id "43418419"
    :title "Effective Scala"
    :description "Points, sharp edges, rough patches"}])

(defn map-using
  "Create a map from a collection, using f to generate the key."
  [coll f]
  (zipmap (map f coll) coll))

(def app-state
  "All application state:

  - A title
  - A place to put the *Vimeo Player*
  - The ID of the video to show first
  - A \"database\" of videos"
  {:title          "Chariot Video Stream (Om.Next)"
   :media-list     {:videos videos}
   :media-player   {:player-element-id "player-place"}
   :selected-video (first videos)})

(defmulti read
  "Return data from the application state, depending on the key given.
  By default, do a simple lookup, but if need be we can write methods
  for arbitarily complex queries."
  (fn [env key params] key))


(defn get-videos
  "Return the videos"
  [state k]
  (let [st @state]
    (into [] (map #(get-in st %)) (get st k))))

;; Look up data by key by default.
;;
;; Return `:not-found` in case it isn't found!
(defmethod read :default
  [{:keys [state query] :as env} k params]
  (println (str "st: " @state
                "\nq: " query
                "\nk: " k
                "\np: " params))
  (let [st @state]
    (if-let [[_ value] (find st k)]
      (let [result value]
        (println "result: " result)
        {:value value})
      (if-let [query (:query env)]
        (let [result (om/db->tree query st st)]
          (println "q-result: " result)
          {:value result})
        (do
          #_(println "result: :not-found")
          {:value :not-found})))))

#_(defmethod read :videos
    "Not working as expected"
  [{:keys [state query] :as env} k params]
  (println (str "Reading :videos"
                "\nst: " @state
                "\nq: " query
                "\nk: " k
                "\np: " params))
  (let [result (get-videos state k)]
    (println "result: " result)
    {:value result}))

(defmethod read :videos
  [{:keys [query state]} k _]
  (let [st @state]
    {:value (om/db->tree query (get st k) st)}))

(defmethod read :media-list
  [{:keys [query state]} k _]
  (let [st @state]
    {:value (om/db->tree query (get st k) st)}))

(defmethod read :media-player
  [{:keys [query state]} k _]
  (let [st @state]
    {:value (om/db->tree query (get st k) st)}))

#_(defmethod read :selected-video
  [{:keys [state] :as env} k params]
  (let [st @state]
    {:value (get-in st (get st k))}))

(defmulti mutate
  "Update application data: keys will be symbols. (Conventionally:
  keywords are used for `read` operations, to look like structure
  accesses, and symbols are used for `mutate` operations"
  om/dispatch)

;; Update the `:selected-video` value using the
;; `:selected-video` parameter
(defmethod mutate 'do/select-video!
  [{:keys [state] :as env} _ {:keys [selected-video] :as params}]
  {:action #(swap! state assoc
                   :selected-video selected-video)
   :value {:keys [:selected-video]}})

(def my-parser
  "Break parser out so I can see it (for development)"
  (om/parser {:read read :mutate mutate}))

;; The Om Reconciler ties state and the parser together
(def reconciler
  (om/reconciler
   {:state app-state
    :parser my-parser}))

#_(defcard-doc
   "## Rendering Om Next components with `om-next-root` and `defcard-om-next`
    The `om-next-root` will render Om Next components, much the way `om.core/add-root!` does.
    It takes one or two arguments. The first argument is the Om Next component. The second (optional)
    argument is either a map with the state to pass to the component, or an Om Next reconciler.
    The `defcard-om-next` is a shortcut to `(defcard (om-next-root ...))`.
    Its arguments are the same of a normal `defcard`, with the following exception:
    after the optional name and documentation, there must be an Om Next component. The argument after that
    is optional, and may either the initial state map, or an Om Next reconciler.
    Please refer to code of this file to see how these Om Next examples are
    built.
    ### One more thing
    - If you want to experience the best of a live-programming environment, don't forget to write reloadable code:
      - `defui ^:once` your components
      - `defonce` your reconcilers!
")

#_(defcard-om-next media-item-test
  "Test MediaItem"
  MediaItem
  (om/reconciler
   {:state (atom {:videos [[:videos/by-id "99999"]]
                  :by-id {"99999" {:id "99999"
                                         :title "Dr Zhivago"
                                         :description "Russia, 1917"}}
                  :id "99998" :title "Dr Zhivago-separate"
                  :description "Russia, 1917 - separate"
                  :selected-video [:videos/by-id "99999"]})
    :parser my-parser})
  {:initial-data [:videos/by-id "99999"]
   :inspect-data true})

(defcard-om-next media-player-app-test
  "Try the full `MediaPlayerApp`"
  MediaPlayerApp
  reconciler
  {:inspect-data true})

(defcard-om-next media-player-test
  "See what the `MediaPlayer` does in an *unrealistic* situation
  where the `:selected-video` element is on the *same* level as
  the `:player-element-id` element.  This won't happen in real life,
  but it's easier to get working."
  MediaPlayer
  (om/reconciler
   {:state {:selected-video (last videos)
            :player-element-id "player-place"}
    :parser my-parser})
  {:inspect-data true})

(defcard-om-next media-player-test-multilevel-data
  "What do we need to do to get the `MediaPlayer` running when the
   data it want is on two *different* levels of the state?"
    (om/ui
     static om/IQuery
     (query [_]
       (let [subquery (om/get-query MediaPlayer)]
         `[{:media-player ~subquery}]))
     Object
     (render [this]
       (let [{media-player-props :media-player} (om/props this)]
         (media-player media-player-props))))
  (om/reconciler
   {:state (assoc-in app-state [:media-player :player-element-id] "mptest-player-id")
    :parser my-parser})
  {:inspect-data true})

(defcard-om-next media-list-test
  "See what the `MediaList` does: you can click on either of the
  two (or more, someday) elements below"
  MediaList
  (om/reconciler
   {:state  {:videos       videos
             :selected-video (first videos)}
    :parser my-parser})
  {:inspect-data true})

(defcard-om-next media-list-test-multilevel-data
  "See what the `MediaList` does: you can click on either of the
  two (or more, someday) elements below. However, this has to
  run where we have multilevel data."
  (om/ui
   static om/IQuery
   (query [_]
     (let [subquery (om/get-query MediaList)]
       `[{:media-list ~subquery}]))
   Object
   (render [this]
     (let [props (om/props this)
           {media-list-props :media-list} (om/props this)]
       (media-list media-list-props))))
  (om/reconciler
   {:normalize true
    :state  {:media-list     {:videos videos}
             :selected-video (first videos)}
    :parser my-parser})
  {:inspect-data true})

(defn main
  "conditionally start the app based on whether the #main-app-area
  node is on the page (it won't be on a devcard page...)"
  []
  (if-let [node (.getElementById js/document "main-app-area")]
    (om/add-root! reconciler MediaPlayerApp node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html

