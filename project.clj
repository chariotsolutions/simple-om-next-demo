(defproject omnext-demo "0.3.0-SNAPSHOT"
  :description "Minimal Om.Next/React demo program"
  :url "https://github.com/chariotsolutions/simple-om-next-demo"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"
  
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [devcards "0.2.2"]
                 [sablono "0.7.4"]
                 [devcards-om-next "0.3.0"]                 
                 [org.clojure/core.async "0.2.391"]
                 ;; need to specify this for sablono
                 ;; when not using devcards
                 [cljsjs/react "15.3.1-0"]
                 [cljsjs/react-dom "15.3.1-0"]
                 [org.omcljs/om "1.0.0-alpha47-SNAPSHOT"]
                 [datascript "0.15.4"]]

  :plugins [[lein-figwheel "0.5.8"]
            [lein-cljsbuild "1.1.4" :exclusions [org.clojure/clojure]]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version"
                   "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]
                  ["vcs" "push"]]

  
  :source-paths ["src"]

  :margauto {:src-dirs ["src" "dev" "test"]
             :port     3000}

  :cljsbuild {
              :builds [{:id           "devcards"
                        :source-paths ["src"]
                        :figwheel     { :devcards true ;; <- note this
                                       ;; :open-urls will pop open your application
                                       ;; in the default browser once Figwheel has
                                       ;; started and complied your application.
                                       ;; Comment this out once it no longer serves you.
                                       :open-urls ["http://localhost:3449/cards.html"]}
                        :compiler     { :main                "omnext-demo.core"
                                       :asset-path           "js/compiled/devcards_out"
                                       :externs              ["vimeo-externs.js"]
                                       :output-to            "resources/public/js/compiled/omnext_demo_devcards.js"
                                       :output-dir           "resources/public/js/compiled/devcards_out"
                                       :source-map-timestamp true }}
                       {:id           "dev"
                        :source-paths ["src"]
                        :figwheel     true
                        :compiler     {:main                 "omnext-demo.core"
                                       :asset-path           "js/compiled/out"
                                       :externs              ["vimeo-externs.js"]
                                       :output-to            "resources/public/js/compiled/omnext_demo.js"
                                       :output-dir           "resources/public/js/compiled/out"
                                       :source-map-timestamp true }}
                       {:id           "prod"
                        :source-paths ["src"]
                        :compiler     {:main          "omnext-demo.core"
                                       :asset-path    "js/compiled/out"
                                       :externs       ["vimeo-externs.js"]
                                       :output-to     "resources/public/js/compiled/omnext_demo.js"
                                       :optimizations :advanced}}]}

  :figwheel { :css-dirs ["resources/public/css"] }

  :profiles {:dev {:dependencies [[binaryage/devtools "0.8.2"]
                                  [figwheel-sidecar "0.5.8"]
                                  [com.cemerick/piggieback "0.2.1"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; for CIDER
                   ;; :plugins [[cider/cider-nrepl "0.12.0"]]
                   :repl-options {; for nREPL dev you really need to limit output
                                  :init             (set! *print-length* 50)
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
