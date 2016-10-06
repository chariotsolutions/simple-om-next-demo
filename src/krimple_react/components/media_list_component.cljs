(ns krimple-react.components.media-list-component
  "Not quite sure what this is all about"
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]
   [krimple-react.components.media-item-component
    :as mic :refer [media-item-component]]))


(defui MediaListComponent
  static om/IQuery
  (query [this]
    (let [subquery (om/get-query mic/MediaItemComponent)]
      `[{:video/items ~subquery}]))
  Object
  (render [this]
    (let [{:keys [video/items]} (om/props this)]
      (println "In MLC:\n" (om/props this) "\nOUT OF MLC")
      (dom/ul #js {:className :list-group}
              (map #(dom/li (clj->js {:key %})
                            (mic/media-item-component (get items %)))
                    (keys items))))))



(def media-list-component (om/factory MediaListComponent))
