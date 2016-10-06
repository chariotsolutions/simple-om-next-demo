(ns krimple-react.components.media-player-component
  "Component hosting the Vimeo media player"
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]))

(defui MediaPlayerComponent
  static om/IQuery
  (query [this]
    [:video/current-id
     :video/selected-video])
  Object
  (render [this]
   (let [{:keys [video/current-id video/selected-video]} (om/props this)]
     (println "Rendering MediaPlayerComponent (perhaps again)..." selected-video)
     (dom/div nil
              (dom/div (clj->js {:data-vimeo-id selected-video
                                 :data-vimeo-width 640
                                 :style {:width 640
                                         :height 480}
                                 :id current-id})))))
  (componentDidUpdate [this prev-props prev-state]
    (let [{:keys [video/selected-video]} (om/props this)]
      (println "In CDU: " (om/props this))
      (if-let [player (om/get-state this :player)]
        (.loadVideo player selected-video)
        (println "No player to load..."))))
  (componentDidMount [this]
   (let [{:keys [video/current-id video/selected-video] :as props} (om/props this)
         _ (println "In componentDidMount: " props)]
     (.log js/console "Somewhere in componentDidMount")
     (if-let [player (om/get-state this :player)]
       (do
         (.log js/console "Re-using existing Vimeo Player...")
         (.loadVideo player selected-video))
       (do
         (.log js/console "Initial creation of Vimeo Player...")
         (om/update-state!
          this assoc :player (Vimeo.Player. current-id
                                            (clj->js {:id selected-video
                                                      :width 640
                                                      :height 480
                                                      :loop false}))))))))

(def media-player-component (om/factory MediaPlayerComponent))
