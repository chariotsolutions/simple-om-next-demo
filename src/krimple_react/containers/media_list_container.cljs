(ns krimple-react.containers.media-list-container
  "Container for the media list (no surprise)"
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]
   [krimple-react.components.media-list-component
    :as media-list-component
    :refer [MediaListComponent media-list-component]]))


(defui MediaListContainer
  static om/IQuery
  (query [this]
    [:video/items :video/selected-video :video/current-id])
  Object
  (render [this]
    (let [{:keys [video/items] :as props} (om/props this)]
      (if (and items (pos? (count items)))
        (dom/div #js {:className :pointlessExtraDiv}
                 (media-list-component props))
        (dom/span nil "Please wait&hellip;")))))

(def media-list-container (om/factory MediaListContainer))
