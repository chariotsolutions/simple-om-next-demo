(ns user
  (:require
   [omnext-demo.shared-definitions :as shared]
   [omnext-demo.core :as core]
   [omnext-demo.media-player-app :as mpa]
   [om.dom :as dom]
   [om.next :as om :refer-macros [defui]]
   ))

(defn try-query
  "Try out a query against the reconciler's live state"
  ([query]
   (shared/my-parser (:config shared/reconciler)
                     query))
  ([query remote]
   (shared/my-parser {:config shared/reconciler}
                     query
                     remote)))
