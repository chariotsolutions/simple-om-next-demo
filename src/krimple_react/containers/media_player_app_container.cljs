(ns krimple-react.containers.media-player-app-container
  "Container for the App itself"
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]
   [krimple-react.components.media-player-app-component
    :as media-player-app-component
    :refer [MediaPlayerAppComponent media-player-app-component]]))

(defui MediaPlayerAppContainer
  Object
  (render [this]
    (dom/div #js {:className :container}
             (media-player-app-component (om/props this)))))

(def media-player-app-container (om/factory MediaPlayerAppContainer))
