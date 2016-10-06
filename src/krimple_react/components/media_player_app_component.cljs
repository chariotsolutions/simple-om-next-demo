(ns krimple-react.components.media-player-app-component
  "Component containing the Media List and Media Player.

  This also provides a title for the app!"
  (:require
   [krimple-react.containers.media-list-container
    :refer [MediaListContainer media-list-container]]
   [krimple-react.containers.media-player-container
    :refer [MediaPlayerContainer media-player-container]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defui MediaPlayerAppComponent
  "Wrap the Media List and the Media Player subcomponents"
  static om/IQuery
  (query [this]
    `[:app/title
      ~@(om/get-query MediaListContainer)
      ~@(om/get-query MediaPlayerContainer)])
  Object
  (render [this]
    (let [{:keys [app/title] :as props} (om/props this)]
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
