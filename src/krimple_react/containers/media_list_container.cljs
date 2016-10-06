(ns krimple-react.containers.media-list-container
  "Container for the media list (no surprise)"
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]
   [krimple-react.components.media-list-component
    :as media-list-component
    :refer [MediaListComponent media-list-component]]))


(defui MediaListContainer
  "Container for a component: not much useful by itself"
  static om/IQuery
  (query [this]
    `[:video/items
      ~@(om/get-query MediaListComponent)])
  Object
  (render [this]
    (let [{:keys [video/items] :as props} (om/props this)]
      (if (and items (pos? (count items)))
        (dom/div #js {:className :pointlessExtraDiv}
          (media-list-component props))
        (dom/span nil "Please wait...")))))

(def media-list-container (om/factory MediaListContainer))
