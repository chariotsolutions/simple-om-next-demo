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
      `[{:videos ~subquery}]))

  Object
  (render [this]
    (let [{:keys [videos] :as props} (om/props this)]
      (println "MIL: other-props: " (dissoc props :videos))
      (if (and videos (pos? (count videos)))
        (dom/div #js {:className :pointlessExtraDiv}
          (dom/ul #js {:className :list-group}
                  (map #(dom/li #js {:key (:id %)}
                                (mi/media-item %))
                       videos)))
        (dom/span nil "Please wait...")))))

(def media-list (om/factory MediaList))
