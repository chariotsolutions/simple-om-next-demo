(ns krimple-react.cards
  "Collect `devcard` definitions here"
  (:require
   [devcards-om-next.core :refer-macros [defcard-om-next om-next-root]]
   [krimple-react.media-player-app
    :refer [MediaPlayerApp media-player-app]]
   [krimple-react.media-item :refer (MediaItem, media-item)]
   [krimple-react.media-list :refer (MediaList media-list)]
   [krimple-react.media-player :refer (MediaPlayer media-player)]
   [krimple-react.media-player-app :refer (MediaPlayerApp media-player-app)]
   [krimple-react.shared-definitions :refer [reconciler my-sender my-parser log-this!]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]
   [sablono.core :as sab :include-macros true])
  (:require-macros
   [devcards.core :as dc :refer [defcard defcard-doc deftest]]))

(defui LoggingToggle
  static om/IQuery
  (query [_]
    '[[:logging-enabled _]])
  Object
  (render [this]
    (let [{:keys [logging-enabled]} (om/props this)
          cb-options {:type :checkbox
                      :id :logging-enabled
                      :value (if logging-enabled "on" "off")
                      :checked logging-enabled
                      :onChange (fn [e]
                                 (om/transact! this '[(toggle-logging!)
                                                      :logging-enabled]))}]
      (dom/div #js {:className :logging-toggle-div}
        (dom/label nil
          (dom/input (clj->js cb-options))
          "Enable logging")))))
(def logging-toggle (om/factory LoggingToggle))

(defcard-om-next logging-status
  "Allow logging to be toggled"
  LoggingToggle
  reconciler
  {:inspect-data true})

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

#_(defcard-om-next media-player-app-test
  "Try the full `MediaPlayerApp`"
  MediaPlayerApp
  reconciler
  {:inspect-data true})

#_(defcard-om-next media-player-test
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

#_(defcard-om-next media-player-test-multilevel-data
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

#_(defcard-om-next media-list-test
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
     (let [ml-subquery (om/get-query MediaList)
           lt-subquery (om/get-query LoggingToggle)]
       `[{:media-list ~ml-subquery}
         ~lt-subquery]))
   Object
   (render [this]
     (log-this! this "rendering media-list")
     (let [props (om/props this)
           _ (log-this! this props)
           {media-list-props :media-list
            logging-enabled :logging-enabled} props]
       (dom/div nil
         (logging-toggle (om/props this))
         (media-list media-list-props)))))
  (om/reconciler
   {:normalize true
    :send my-sender
    :remotes [:vimeo]
    :state  {:logging-enabled true
             :media-list     {:videos []
                              :sort-order :unsorted}
             :selected-video {:id "NO VIDEO"}}
    :parser my-parser})
  {:inspect-data true})
