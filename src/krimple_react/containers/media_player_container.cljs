(ns krimple-react.containers.media-player-container
  "Container for the MediaPlayer"
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]
   [krimple-react.components.media-player-component
    :refer [MediaPlayerComponent media-player-component]]
   ))

(defui MediaPlayerContainer
  static om/IQuery
  (query [this]
    (om/get-query MediaPlayerComponent))
  Object
  (render [this]
    (let [props (om/props this)
          {:keys [video/current-id]} props]
      (dom/div nil
               (media-player-component (om/props this))))))

(def media-player-container (om/factory MediaPlayerContainer))
