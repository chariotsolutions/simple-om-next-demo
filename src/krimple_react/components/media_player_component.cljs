(ns krimple-react.components.media-player-component
  "Component hosting the Vimeo media player.

  After the component is \"mounted\", it creates an instance of the
  *Vimeo Player*, and stores it in this component's local state.
  Because this component includes `:video/selected-video` in its
  **query**, it will be re-rendered when the video-selection changes,
  and so can load the video into the *Player* that it manages."
  (:require
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))

(defui MediaPlayerComponent
  "Manage a *Vimeo Media Player*, updating it as the selected video
  changes.

  The strangely-named `current-id` is actually the ID of the element
  where the *Vimeo Player* is displayed. (Or at least so it seemed
  in the original app I derived this from.)"
  static om/IQuery
  (query [this]
    [:video/current-id
     :video/selected-video])
  Object
  (render [this]
    (let [{:keys [video/current-id video/selected-video]} (om/props this)]
      (dom/div nil
        (dom/div (clj->js {:data-vimeo-id selected-video
                           :data-vimeo-width 640
                           :style {:width 640
                                   :height 480}
                           :id current-id})))))

  (componentDidUpdate [this _ _]
    ;; We use neither `prev-props` nor `prev-state`
    (let [{:keys [video/selected-video]} (om/props this)]
      (if-let [player (om/get-state this :player)]
        (.loadVideo player selected-video)
        (println "No player to load..."))))

  (componentDidMount [this]
    (let [{:keys [video/current-id video/selected-video]} (om/props this)]
      (if-let [player (om/get-state this :player)]
        (do
          (.log js/console "Re-using existing Vimeo Player...")
          (.loadVideo player selected-video))
        (om/update-state!
         this assoc :player (Vimeo.Player. current-id
                                           (clj->js {:id selected-video
                                                     :width 640
                                                     :height 480
                                                     :loop false})))))))

(def media-player-component (om/factory MediaPlayerComponent))
