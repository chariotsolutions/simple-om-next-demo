(ns krimple-react.media-player-app
  "Main component for the application

  - Displays the title (from app state)
  - Contains the list of available media items
  - Contains the Media Player"
  (:require
   [krimple-react.media-list :as ml :refer [MediaList]]
   [krimple-react.media-player :as mp :refer [MediaPlayer]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defui ^:once MediaPlayerApp
  "Component containing a list of Media items and a player to show them"
  static om/IQuery
  (query [this]
    (let [ml-query (om/get-query MediaList)
          mp-query (om/get-query MediaPlayer)]
      `[:title
        {:media-list ~ml-query}
        {:media-player ~mp-query}]))

  Object
  (render [this]
    (dom/div #js {:className :container}
      (let [{:keys [title media-list media-player] :as props} (om/props this)]
        (dom/div #js {:className :container}
          (dom/div #js {:className :row}
            (dom/h1 nil title)
            (dom/hr nil))
          (dom/div #js {:className :row}
            (dom/div #js {:className :col-md-5}
              (println "media-list props: " media-list)
              (ml/media-list media-list))
            (dom/div #js {:className :col-md-2})
            (dom/div #js {:className :col-md-5}
              (println "media-player props: " media-player)
              (mp/media-player media-player))))))))

(def media-player-app (om/factory MediaPlayerApp))
