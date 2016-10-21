(ns omnext-demo.cards
  "Collect `devcard` definitions here

  [**devcards**](https://github.com/bhauman/devcards) \"provide a visual REPL experience for ClojureScript\""
  (:require
   [devcards-om-next.core :refer-macros [defcard-om-next om-next-root]]
   [omnext-demo.media-player-app
    :refer [MediaPlayerApp media-player-app]]
   [omnext-demo.media-item :refer (MediaItem, media-item)]
   [omnext-demo.media-list :refer (MediaList media-list)]
   [omnext-demo.media-player :refer (MediaPlayer media-player)]
   [omnext-demo.media-player-app :refer (MediaPlayerApp media-player-app)]
   [omnext-demo.shared-definitions
    :refer [app-state reconciler my-sender my-parser]]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]
   [sablono.core :as sab :include-macros true])
  (:require-macros
   [devcards.core :as dc :refer [defcard defcard-doc deftest]]))

(enable-console-print!)

(defcard media-list-test-multilevel-data
  "See what the `MediaList` does: you can click on either of the
  two (or more, someday) elements below. However, this has to
  run where we have multilevel data."
  (media-list {:app/videos [{:video/id "ABC"
                  :video/title "First Video"
                  :video/description "Description of first video"
                  :video/thumbnail_medium "https://i.vimeocdn.com/video/584100643_200x150.webp"}
                 {:video/id 175888220
                  :video/title "Serverless Design Patterns for the Enterprise with AWS Lambda"
                  :video/description "Another Description"
                  :video/thumbnail_medium "https://i.vimeocdn.com/video/583056926_200x150.webp"}]})
  {:inspect-data true})

(defcard-om-next full-application
  "Test the full application through `MediaPlayerApp`"
  MediaPlayerApp
  reconciler
  app-state
  {:inspect-data false})
