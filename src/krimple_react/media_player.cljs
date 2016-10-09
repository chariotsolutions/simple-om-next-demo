(ns krimple-react.media-player
  "Component hosting the Vimeo media player.

  After the component is \"mounted\", it creates an instance of the
  *Vimeo Player*, and stores it in this component's local state.
  Because this component includes `:video/selected-video` in its
  **query**, it will be re-rendered when the video-selection changes,
  and so can load the video into the *Player* that it manages."
  (:require
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]))


(defui ^:once MediaPlayer
  "Manage a *Vimeo Media Player*, updating it as the selected video
  changes.

  `player-element-id` is the ID of the element where the *Vimeo
  Player* is displayed."
  static om/IQuery
  (query [_]
    '[:player-element-id [:selected-video _]])

  Object
  (render [this]
    (let [{player-element-id :player-element-id
           {selected-video-id :id} :selected-video} (om/props this)]
      (dom/div nil
        (dom/div (clj->js {:data-vimeo-id    selected-video-id
                           :data-vimeo-width 640
                           :style            {:width  640
                                              :height 480}
                           :id               player-element-id})))))

  (componentDidUpdate [this _ _]
    ;; We use neither `prev-props` nor `prev-state`
    (let [{{selected-video-id :id} :selected-video} (om/props this)]
      (if-let [player (om/get-state this :player)]
        (.loadVideo player selected-video-id)
        (println "No player to load..."))))

  (componentDidMount [this]
    (let [props (om/props this)
          _ (println "CDM: props: " props)
          {player-element-id :player-element-id
           {selected-video-id :id} '[:selected-video _]} props]
      (if-let [player (om/get-state this :player)]
        (do
          (.log js/console (str "CDM: Re-using existing Vimeo Player...: '" selected-video-id "'"))
          (.loadVideo player selected-video-id))
        (try
          (println "CDM: Attempting to create a player for video '" selected-video-id
                   "' at '" player-element-id "'")
          (let [player-options #js {:id     selected-video-id
                                    :width  640
                                    :height 480
                                    :loop   false}
                player         (Vimeo.Player. player-element-id
                                              player-options)]
            (om/update-state!
             this assoc :player player))
          (catch js/Error e
            (js/alert (str "CDM: Player not available\n" e))))))))

(def media-player (om/factory MediaPlayer))
