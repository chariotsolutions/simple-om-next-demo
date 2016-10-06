(ns krimple-react.components.media-item-component
  "Display an individual media item from Vimeo

  Given properties `:item/id`, `:item/title`, and `:item/description`,
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
  (let [selected-video (:selected-video (om/props this))
        this-video     (:item-id (om/get-computed this))]
    (= this-video selected-video)))

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

(defui MediaItemComponent
  "Represent a single video. If clicked, request selection of this video."
  static om/Ident
  ;; Given something with an `:item/id`, map it to a media item in the
  ;; app's state
  (ident [_ {:keys [item/id]}]
    [:video/items id])
  static om/IQuery
  ;; This is what we need to render our data. `:video/selected-video`
  ;; will be passed in as a *computed* value
  (query [this]
    [:item/id :item/title :item/description])
  Object
  (render [this]
    (let [{:keys [item/id item/title item/description]} (om/props this)]
      (dom/div #js {:onClick   (fn [e]
                                 (om/transact! this `[(do/select-video! {:selected-video ~id})]))
                    :className (str "list-group-item "
                                    (if (is-current-video? this) "active" ""))}
        (dom/h3 nil title)
        ;; Limit the description length to that of a Tweet...
        (dom/p nil (truncate-description description 140))))))


(def media-item-component
  "Return a factory that builds these things

  Note the use of `:keyfn` to identify the function that *React* uses
  to uniquely identify items of the same type in a series."
  (om/factory MediaItemComponent {:keyfn :id}))
