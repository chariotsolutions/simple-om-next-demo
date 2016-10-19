(ns krimple-react.media-item
  "Display an individual media item from Vimeo

  Given properties `:video/id`, `:video/title`, and `:video/description`,
  display the *title* and *description*, truncating the *description*
  if need be."
  (:require
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defn truncate-description
  "If the description is too long, shorten it and add an ellipsis.

  Not a real ellipsis: just three dots. Figuring out how to add an
  HTML entity is currently above my pay grade.

  Oh, if the description isn't a string, return an empty string."
  [description max-length]
  (if (string? description)
    (str (subs description 0 max-length)
         (if (> (count description) max-length) "..." ""))
    ""))

(defn media-item-clicked
  "Handle a click on this item"
  [this _]
  (let [props (om/props this)
        my-video-id (om/ident this props)]
    (om/transact!
     this `[(do/select-video! {:selected-video ~my-video-id})
            :selected-video])))

(defui ^:once MediaItem
  "Represent a single video. If clicked, request selection of this video."
  static om/Ident
  ;; Given something with a `:video/id`, map it to a media item in the
  ;; app's state
  (ident [_ {:keys [video/id]}]
    [:videos/by-id id])
  static om/IQuery
  ;; This is what we need to render our data. `:selected-video`
  ;; is from the top-level
  (query [this]
    '[:video/id :video/title :video/description {:video/related-videos [:video/id :video/title]}])
  Object
  (render [this]
    (let [{:keys [video/id video/title video/description video/related-videos]} (om/props this)
          {:keys [active?] :or {active? false}} (om/get-computed this)
          when-current (fn ([is]
                            (if active? is ""))
                           ([is isnt]
                            (if active? is isnt)))
          div-id (str "video-" id "-container")
          details-id (str "video-" id "-details")
          summary-id (str "video-" id "-summary")]
      (dom/div
        (clj->js
         {:id div-id
          :onClick #(media-item-clicked this %)
          :className (str "list-group-item "
                          (when-current "active" ""))
          :onMouseOver
          (fn [e]
            (let [target (.getElementById js/document details-id)]
              (set! (.-open target) true)))
          :onMouseOut
          (fn [e]
            (let [target (.getElementById js/document details-id)]
              (set! (.-open target) false)))
          :style  {:margin "12px 0px"
                   :borderWidth 5
                   :backgroundColor "#efefef"
                   :borderStyle (when-current "inset" "outset")}})
        (dom/details (clj->js
                      {:id details-id})
          (dom/summary #js {:id summary-id} title)
          #_(dom/h4 nil (when-current "Now playing..." ""))
          ;; Limit the description length to that of a Tweet...
          (dom/p nil (truncate-description description 140))
          (when-not (empty? related-videos)
            (let [main-id id]
              (dom/div nil
                (dom/p nil "See also...")
                (dom/ul #js {:className "related-videos"}
                  (map
                   (fn [{:keys [video/id video/title] :as related}]
                     (let [li-id (str main-id "-related-" id)]
                       (dom/li (clj->js {:className "related-video"
                                         :id li-id
                                         :key li-id})
                               title)))
                   ;; Some related-video entries will be empty maps, because
                   ;; the video hasn't been loaded (or normalized) yet. Skip
                   ;; them the first time, and they will be re-rendered when
                   ;; the data loads.  (Thanks, React/Om!)
                   (remove empty? related-videos)))))))))))


(def media-item
  "Return a factory that builds these things

  Note the use of `:keyfn` to identify the function that *React* uses
  to uniquely identify items of the same type in a series."
  (om/factory MediaItem {:keyfn :video/id}))
