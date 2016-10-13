(ns krimple-react.core
  "Single-Page Application to display one of *two* Vimeo videos.

  This was initially derived from a skeletal [React
  presentation](https://github.com/krimple/libertyjs-react-talk-public)
  for the [LibertyJS User Group](http://www.libertyjs.com/)."
  (:require
   [cljs.test :refer-macros [is async]]
   [datascript.core :as d]
   [goog.dom :as gdom]
   [krimple-react.media-player-app :refer (MediaPlayerApp media-player-app)]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]
   [sablono.core :as sab :include-macros true]
   [krimple-react.cards :as cards]
   [krimple-react.shared-definitions :as shared
    :refer [reconciler]]))

;;; Allow us to print to the Console
(enable-console-print!)

(defn main
  "conditionally start the app based on whether the #main-app-area
  node is on the page (it won't be on a devcard page...)"
  []
  (if-let [node (.getElementById js/document "main-app-area")]
    (om/add-root! reconciler MediaPlayerApp node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html

