(ns krimple-react.components.media-item-component
  "Display an individual media item from Video"
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]))


(defn is-current-video?
  "Return true if the props suggest this is the current video"
  [this]
  (let [selected-video (:selected-video (om/props this))
        this-video (:item-id (om/get-computed this))]
    (= this-video selected-video)))

(defui MediaItemComponent
  static om/Ident
  (ident [this {:keys [item/id]}]
    [:video/items id])
  static om/IQuery
  (query [this]
    [:item/id :item/title :item/description])
  Object
  (render [this]
    (let [props (om/props this)
          {:keys [item/id item/title item/description]} props]
      (dom/div #js {:onClick (fn [e]
                               (om/transact! this `[(do/select-video! {:selected-video ~id})]))
                    :className (str "list-group-item "
                                    (if (is-current-video? this) "active" ""))}
        (dom/h3 nil title)
        (do
          (println "Description: " description)
          (dom/p nil (str (subs (or description "No description") 0 300)
                          (if (> (count description) 300)
                            "..."
                            ""))))))))


(def media-item-component (om/factory MediaItemComponent {:keyfn :id}))
