(ns krimple-react.components.media-player-app-component
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]
   [krimple-react.containers.media-player-container :refer [media-player-container]]
   [krimple-react.containers.media-list-container :refer [media-list-container]]))

(defui MediaPlayerAppComponent
  static om/IQuery
  (query [this]
    [:app/title
     :video/items :video/current-id :video/selected-video])
  Object
  (render [this]
    (let [props (om/props this)
          {:keys [app/title video/items video/current-id video/selected-video]} props]
      (dom/div #js {:className :container}
        (dom/div #js {:className :row}
          (dom/h1 nil title)
          (dom/hr nil))
        (dom/div #js {:className :row}
          (dom/div #js {:className :col-md-5}
            (media-list-container props))
          (dom/div #js {:className :col-md-2})
          (dom/div #js {:className :col-md-5}
            (media-player-container props)))))))


(def media-player-app-component (om/factory MediaPlayerAppComponent))
