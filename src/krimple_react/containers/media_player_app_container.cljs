(ns krimple-react.containers.media-player-app-container
  "Container for the App itself"
  (:require
   [krimple-react.components.media-player-app-component
    :as media-player-app-component
    :refer [MediaPlayerAppComponent media-player-app-component]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defui MediaPlayerAppContainer
  "Container for the app component"
  static om/IQuery
  (query [this]
    (om/get-query MediaPlayerAppComponent))
  Object
  (render [this]
    (dom/div #js {:className :container}
      (media-player-app-component (om/props this)))))

(def media-player-app-container (om/factory MediaPlayerAppContainer))
