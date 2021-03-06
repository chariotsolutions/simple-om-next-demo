(ns omnext-demo.media-player-app
  "Main component for the application

  - Displays the title (from app state)
  - Contains the list of available media items
  - Contains the Media Player"
  (:require
   [omnext-demo.media-list :as ml :refer [MediaList]]
   [omnext-demo.media-player :as mp :refer [MediaPlayer]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defui ^:once MediaPlayerApp
  "
  Component containing a list of Media items and a player to show them.

  This component is a root component, and so should not need to extend
  the `IQuery`: everything is passed to it.
"
  static om/IQuery
  (query [this]
    (let [ml-query (om/get-query MediaList)
          mp-query (om/get-query MediaPlayer)]
      `[:app/title
        {:app/media-list ~ml-query}
        {:app/media-player ~mp-query}]))

  Object
  (render [this]
    (dom/div #js {:className "container"}
      (let [{:keys [app/title app/media-list app/media-player]} (om/props this)]
        (dom/div #js {:className "container"}
          (dom/div #js {:className "row"}
            (dom/h1 nil title)
            (dom/hr nil))
          (dom/div #js {:className "row"}
            (dom/div #js {:className "col-md-5"}
              (ml/media-list media-list))
            (dom/div #js {:className "col-md-2"})
            (dom/div #js {:className "col-md-5"}
              (mp/media-player media-player))))))))

(def media-player-app (om/factory MediaPlayerApp))
