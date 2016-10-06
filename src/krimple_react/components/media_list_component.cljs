(ns krimple-react.components.media-list-component
  "Renders an unordered list of Media Items

   Note that because this generates a series of otherwise identical LI
  elements, *React* requires a unique `:key` attribute on each."
  (:require
   [krimple-react.components.media-item-component
    :as mic :refer [media-item-component]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))


(defui MediaListComponent
  "Generate a `UL` of keyed `LI` items, each holding a Media Item"
  static om/IQuery
  (query [this]
    (let [subquery (om/get-query mic/MediaItemComponent)]
      `[{:video/items ~subquery}]))
  Object
  (render [this]
    (let [{:keys [video/items]} (om/props this)]
      (dom/ul #js {:className :list-group}
              (map #(dom/li #js {:key %}
                            (mic/media-item-component (get items %)))
                    (keys items))))))



(def media-list-component (om/factory MediaListComponent))
