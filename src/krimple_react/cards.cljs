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
   [krimple-react.shared-definitions
    :refer [app-state reconciler my-sender my-parser log-this!]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]
   [sablono.core :as sab :include-macros true])
  (:require-macros
   [devcards.core :as dc :refer [defcard defcard-doc deftest]]))

(enable-console-print!)

(defn logging-changed
  "Mutate the state, enabling or disabling logging"
  [this event]
  (let [target (.-target event)
        checked (.-checked target)]
    (om/transact! this
                  `[(do/set-logging! {:logging-enabled ~checked})
                    :logging-enabled])))

(defui LoggingToggle
  static om/IQuery
  (query [_]
    '[:logging-enabled])
  Object
  (render [this]
    (let [{:keys [logging-enabled]} (om/props this)
          cb-options {:type :checkbox
                      :id :logging-enabled
                      :value (if logging-enabled "on" "off")
                      :checked logging-enabled
                      :onChange #(logging-changed this %)}]
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


