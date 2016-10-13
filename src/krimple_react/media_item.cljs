(ns krimple-react.media-item
  "Display an individual media item from Vimeo

  Given properties `:id`, `:title`, and `:description`,
  display the *title* and *description*, truncating the *description*
  if need be.

  **Not Yet Supported:** add the `active` class if this video is the
  one currently selected for playing."
  (:require
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defn is-current-video?
  "Return true if the props suggest this is the current video"
  [this]
  (let [{:keys [id selected-video]} (om/props this)]
    (= id (:id selected-video))))

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
  ;; Given something with an `:id`, map it to a media item in the
  ;; app's state
  (ident [_ {:keys [id]}]
    [:videos/by-id id])
  static om/IQuery
  ;; This is what we need to render our data. `:selected-video`
  ;; is from the top-level
  (query [this]
    '[:id :title :description
      [:selected-video _]])
  Object
  (render [this]
    (let [{:keys [id title description selected-video]} (om/props this)
          when-current (fn ([is]
                            (if (is-current-video? this) is ""))
                           ([is isnt]
                            (if (is-current-video? this) is isnt)))]
      (dom/div
        (clj->js
         {:onClick #(media-item-clicked this %)
          :className (str "list-group-item "
                          (when-current "active" ""))
          :style  {:margin "12px 0px"
                   :borderWidth 5
                   :backgroundColor "#efefef"
                   :borderStyle (when-current "inset" "outset")}})
        (dom/h3 nil title)
        #_(dom/h4 nil (when-current "Now playing..." ""))
        ;; Limit the description length to that of a Tweet...
        (dom/p nil (truncate-description description 140))))))


(def media-item
  "Return a factory that builds these things

  Note the use of `:keyfn` to identify the function that *React* uses
  to uniquely identify items of the same type in a series."
  (om/factory MediaItem {:keyfn :id}))
