(ns krimple-react.containers.media-player-container
  "Container for the MediaPlayer"
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]
   [krimple-react.components.media-player-component
    :refer [MediaPlayerComponent media-player-component]]
   ))

(defui MediaPlayerContainer
  "Container for Media-Player component"
  static om/IQuery
  (query [this]
    (om/get-query MediaPlayerComponent))
  Object
  (render [this]
    (let [props (om/props this)]
      (dom/div nil
        (media-player-component (om/props this))))))

(def media-player-container (om/factory MediaPlayerContainer))
