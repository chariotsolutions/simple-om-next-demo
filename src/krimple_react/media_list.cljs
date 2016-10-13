(ns krimple-react.media-list
  "Component displaying a list of Media Items for selection

  Note that because this generates a series of otherwise identical LI
  elements, *React* requires a unique `:key` attribute on each."
  (:require
   [krimple-react.media-item :as mi :refer [media-item]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defui ^:once MediaList
  "Generate a `UL` of keyed `LI` items, each holding a Media Item"
  static om/IQuery
  (query [this]
    (let [subquery (om/get-query mi/MediaItem)]
      `[{:videos ~subquery}
        :sort-order]))

  Object
  (render [this]
    (let [{:keys [videos] :as props} (om/props this)]
      (dom/div #js {:className :pointlessExtraDiv}
        #_(dom/fieldset (clj->js {:style {:borderStyle :groove}})
          (dom/label nil
            (dom/input #js {:key :unsorted
                            :type "radio"
                            :checked true})
            "Unsorted")
          (dom/label nil
            (dom/input #js {:key :title
                            :type "radio"
                            :checked false})
            "Title")
          (dom/label nil
            (dom/input #js {:key :description
                            :type "radio"
                            :checked false})
            "Description"))
        (if (and videos (pos? (count videos)))
          (dom/ul #js {:className :list-group}
            (map #(dom/li #js {:key (:id %)}
                          (mi/media-item %))
                 videos))
          (dom/span nil "Please wait..."))))))

(def media-list (om/factory MediaList))
