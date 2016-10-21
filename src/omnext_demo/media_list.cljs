(ns omnext-demo.media-list
  "Component displaying a list of Media Items for selection

  Note that because this generates a series of otherwise identical LI
  elements, *React* requires a unique `:key` attribute on each."
  (:require
   [omnext-demo.media-item :as mi :refer [media-item]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defui ^:once MediaList
  "Generate a `UL` of keyed `LI` items, each holding a Media Item"
  static om/IQuery
  (query [this]
    (let [subquery (om/get-query mi/MediaItem)]
      `[{:app/videos ~subquery}]))

  Object
  (render [this]
    (let [{:keys [app/videos] :as props} (om/props this)]
      (dom/div #js {:className "pointlessExtraDiv"}
        (if (and videos (pos? (count videos)))
          (dom/ul #js {:className "list-group"}
            (map #(dom/li (clj->js {:key (:video/id %)
                                    :display "inline-block"})
                          (mi/media-item %))
                 videos))
          (dom/span nil "Please wait..."))))))

(def media-list (om/factory MediaList))
