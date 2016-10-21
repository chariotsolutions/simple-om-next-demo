(ns omnext-demo.video-catalog)

(def chariot-solutions
  {:video/user_id               10064545
   :video/user_name            "Chariot Solutions"
   :video/user_url             "https://vimeo.com/chariotsolutions"
   :video/user_portrait_small  "https://i.vimeocdn.com/portrait/3571252_30x30.webp"
   :video/user_portrait_medium "https://i.vimeocdn.com/portrait/3571252_75x75.webp"
   :video/user_portrait_large  "https://i.vimeocdn.com/portrait/3571252_100x100.webp"
   :video/user_portrait_huge   "https://i.vimeocdn.com/portrait/3571252_300x300.webp"})


(def video-catalog
  [{:video/id 186363548
    :video/title "Understanding Performance with DTrace (While The Customer Yells at You): A Case Study"
    :video/description "You\u2019ve built some software that users love, and while its use starts to become more frequent it also becomes more critical. Suddenly the failures, inevitable and previously merely a nuisance, have become critical and emotionally fraught as your users see reputation, revenue, and even job security imperiled!<br />\r\n<br />\r\nBugs come in many flavors. None quicken the pulse as much as a critical system suffering from poor performance. They can be hard to drive to root cause, hard to fix, hard to reproduce, and even hard for the user to describe beyond \u201cit\u2019s slow (fix it!)\u201d. Fortunately we\u2019re not completely alone in those lonely moments: we have the lessons and\u2014critically\u2014the tools from the engineers who have survived similar situations.<br />\r\n<br />\r\nDTrace is one such tool, born from experiences debugging functional and performance problems on the world\u2019s most critical systems for over a decade. In this talk we will walk through the basics of DTrace and then focus on a case study of using it to solve a performance problem while turning an audibly angry customer into a happy one."
    :video/url "https://vimeo.com/186363548"
    :video/upload_date "2016-10-10 23:41:23"
    :video/thumbnail_small "https://i.vimeocdn.com/video/596438475_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/596438475_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/596438475_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 6
    :video/stats_number_of_comments 0
    :video/duration 3253
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 185997426
    :video/title "Security Vulnerabilities in Third Party Code: FIX ALL THE THINGS!"
    :video/description "Many developers today are turning to well established third-party libraries to speed the development process and realize quality improvements over creating an in-house proprietary font parsing or image rendering library from the ground up. Efficiency comes at a cost though: a single application may have as many as 100 different third party libraries implemented. The result is that third-party and open source libraries have the ability to spread a single vulnerability across multiple products- exposing enterprises and requiring software vendors and IT organizations to patch the same vulnerability repeatedly.<br />\n<br />\nHow big of a problem is this? What libraries are the biggest offenders for spreading pestilence? And what can be done to minimize this problem? This presentation will dive deep into vulnerability data and explore the source and spread of these vulnerabilities through products \u2013 as well as actions developers, the security research community, and enterprise customers can take to address this problem."
    :video/url "https://vimeo.com/185997426"
    :video/upload_date "2016-10-07 13:57:27"
    :video/thumbnail_small "https://i.vimeocdn.com/video/595916647_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/595916647_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/595916647_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 2
    :video/stats_number_of_comments 0
    :video/duration 3856
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 184394491
    :video/title "Why does functional programming even matter?"
    :video/description "Clients often ask us to demystify functional programming.  This video covers three reasons why functional programming is important."
    :video/url "https://vimeo.com/184394491"
    :video/upload_date "2016-09-26 14:43:23"
    :video/thumbnail_small "https://i.vimeocdn.com/video/593824574_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/593824574_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/593824574_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 4
    :video/stats_number_of_comments 0
    :video/duration 133
    :video/width 1920
    :video/height 1080
    :video/tags "functional programming, clojure, java, scala, java8, cloud"
    :video/embed_privacy "anywhere"},
   {:video/id 181669895
    :video/title "Exploring Wikipedia with Apache Spark: A Live Coding Demo"
    :video/description "The real power and value proposition of Apache Spark is in creating unified use cases combining batch analysis, stream analysis, SQL, machine learning, graph processing and visualizations. In this live coding demo, Sameer will use various Wikipedia datasets to build a dashboard about what is happening in the world during his talk. The application will connect to the live Edits stream of Wikipedia and join it with other Wikipedia datasets to derive interesting insights about what\u2019s trending on the planet."
    :video/url "https://vimeo.com/181669895"
    :video/upload_date "2016-09-06 14:11:45"
    :video/thumbnail_small "https://i.vimeocdn.com/video/590302513_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/590302513_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/590302513_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 8
    :video/stats_number_of_comments 0
    :video/duration 3546
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 181130406
    :video/title "WebAssembly: A New Compilation Target for the Web"
    :video/description "WebAssembly is an emerging standard which defines a new, portable, binary format to serve as a safe and efficient compiler target for the Web. Driven by active cross-browser collaboration, WebAssembly is rapidly taking shape and should be coming in the future to a browser near you. What does this new addition to the open Web platform mean for developers? This talk will provide an overview of the design of WebAssembly and explain how WebAssembly can be used to both bring existing codebases to the Web as well as complement modern web apps written in JS and HTML5. The talk will also cover future directions for WebAssembly such as supporting languages beyond C/C++ and providing tighter integration with JS and Web APIs. If you care about large code, load time, predictable performance, compiling to the web, alternative programming languages or you are using a framework that does, come learn about WebAssembly."
    :video/url "https://vimeo.com/181130406"
    :video/upload_date "2016-09-01 21:07:27"
    :video/thumbnail_small "https://i.vimeocdn.com/video/589589824_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/589589824_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/589589824_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 33
    :video/stats_number_of_comments 0
    :video/duration 2504
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 181117905
    :video/title "From Zero to Application Delivery with NixOS"
    :video/description "[*Due to technical difficulties, this screencast contains some audio issues. We apologize for any inconvenience.]<br />\r\n<br />\r\nManaging configurations for different kinds of nodes and cloud resources in a microservice architecture can be an operational nightmare, especially if not managed with the application codebase. CI and CD job environments often tend to stray from production configuration yielding their results unpredictable at best, or producing false positives in the worst case. Code pushes to staging and production can have unintended consequences which often can\u2019t be inspected fully on a dry run.<br />\r\n<br />\r\nThis session will show you a toolchain and immutable infrastructure principles that will allow you to define your infrastructure in code versioned alongside your application code that will give you repeatable configuration, ephemeral testing environments, consistent CI/CD environments, and diffable dependency transparency all before pushing changes to production."
    :video/url "https://vimeo.com/181117905"
    :video/upload_date "2016-09-01 18:47:20"
    :video/thumbnail_small "https://i.vimeocdn.com/video/589574467_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/589574467_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/589574467_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 11
    :video/stats_number_of_comments 0
    :video/duration 2876
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 180687605
    :video/title "Philly ETE 2016  - Building Wireless Sensors - Don Coleman"
    :video/description "Inexpensive wireless micro-controllers are everywhere. This session will look at building wireless sensors on a variety of hardware: the super low cost ESP8266, the Particle Photon and it\u2019s cloud services, and the new Arduino MKR1000. In addition to building connected devices, I\u2019ll discuss some options for collecting, storing, and visualizing the sensor data."
    :video/url "https://vimeo.com/180687605"
    :video/upload_date "2016-08-29 23:46:26"
    :video/thumbnail_small "https://i.vimeocdn.com/video/588987824_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/588987824_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/588987824_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 13
    :video/stats_number_of_comments 0
    :video/duration 2459
    :video/width 1920
    :video/height 1080
    :video/tags "IoT, sensors, Arduino, LE Bluetooth, Particle Photon"
    :video/embed_privacy "anywhere"},
   {:video/id 179160976
    :video/title "Adventures in Elm: Events, Reproducibility, and Kindness"
    :video/description "What do you get when you combine strict functional programming with heavy user interaction? Challenges, and unexpected freedoms.<br />\r\n<br />\r\nElm is a purely functional language for the browser. It compiles to JavaScript \u2014 after enforcing immutability, types, semantic versioning, and tight boundaries for user and server interactions.<br />\r\n<br />\r\nWorking within these restrictions, I find my programming principles turned upside down. Small components? Who needs them. Global state? No problem. New principles emerge instead: events, reproducibility, kindness in times of error.<br />\r\n<br />\r\nThis session gives an overview of Elm, then focuses on the Elm Architecture: how it overturns what is essential in object-oriented and even backend functional programming."
    :video/url "https://vimeo.com/179160976"
    :video/upload_date "2016-08-17 04:44:06"
    :video/thumbnail_small "https://i.vimeocdn.com/video/587048218_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/587048218_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/587048218_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 17
    :video/stats_number_of_comments 0
    :video/duration 3201
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 179120365
    :video/title "Rust in Production"
    :video/description "Rust is a systems programming language from Mozilla that focuses on safety, speed, and concurrency. Rust reached 1.0 a year ago, and so there\u2019s a question everyone is asking: how has 1.0 tested in production? Is the language \u201cready\u201d yet? In this talk, Steve will give an overview of Rust\u2019s value proposition, focusing on examples and anecdotes from companies using Rust in production today."
    :video/url "https://vimeo.com/179120365"
    :video/upload_date "2016-08-16 19:15:02"
    :video/thumbnail_small "https://i.vimeocdn.com/video/586934693_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/586934693_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/586934693_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 22
    :video/stats_number_of_comments 0
    :video/duration 3566
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 178516651
    :video/title "Move Deliberately and Don\u2019t Break Anything: Lessons from the Evolution of Java"
    :video/description "Programming language design is not just about type theory and grammars. For evolving a mature programming language like Java, it is about finding ways to add capabilities while maintaining compatibility, both with existing code and with the expectations and mental models of 9 million or so developers. In this talk, Java Language Architect Brian Goetz looks at some of the challenges and lessons of steering Java through major evolutionary changes, and a sneak peek at where the Java platform is headed."
    :video/url "https://vimeo.com/178516651"
    :video/upload_date "2016-08-11 15:49:03"
    :video/thumbnail_small "https://i.vimeocdn.com/video/586176405_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/586176405_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/586176405_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 1
    :video/stats_number_of_comments 0
    :video/duration 3520
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 177742462
    :video/title "The future of container-enabled infrastructure"
    :video/description "Open Source communities have been pushing the state of art in containers, schedulers, and distributed systems in the last two years. The excitement in the community at the possibilities these technologies unlock is continuing to build.<br />\r\n<br />\r\nBut, what are we solving for? How does this make your infrastructure better?<br />\r\n<br />\r\nThis talk will describe how bringing these technologies together will create more reliable and greatly more trusted server infrastructure. And it will dive into how you can take steps to start taking advantage of these technologies today and moving into the future."
    :video/url "https://vimeo.com/177742462"
    :video/upload_date "2016-08-05 14:34:29"
    :video/thumbnail_small "https://i.vimeocdn.com/video/585217300_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/585217300_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/585217300_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 6
    :video/stats_number_of_comments 0
    :video/duration 3408
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 176775419
    :video/title "Code Quality in Practice"
    :video/description "We started Code Climate with a simple hypothesis: static analysis can help developers ship better code, faster. Five years later, we analyze over one trillion lines of code each day spanning a wide variety of programming languages, and along the way we\u2019ve learned a lot about code quality itself: what it means, why you want it, how you get it, and more.<br />\r\n<br />\r\nThis talk will cover some of the more surprising insights, including what makes a code metric valuable, when unmaintainable code may be preferable, and the number one thing that prevents most developers from maintaining quality code over time. There will be plenty of time for Q&A throughout, so come prepared to discover and discuss."
    :video/url "https://vimeo.com/176775419"
    :video/upload_date "2016-07-29 13:35:51"
    :video/thumbnail_small "https://i.vimeocdn.com/video/584100643_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/584100643_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/584100643_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 22
    :video/stats_number_of_comments 0
    :video/duration 3589
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 175888220
    :video/title "Serverless Design Patterns for the Enterprise with AWS Lambda"
    :video/description "[*Due to technical difficulties, this screencast contains some sections with audio issues. We apologize for any inconvenience.]<br />\r\n<br />\r\nApps no longer just run on smartphones and tablets \u2013 they process verbal commands we speak to devices like Amazon Echo, run as bots in Slack channels, and are rapidly evolving customer experiences that span a range of IoT devices in homes, cars, offices, and industrial settings. Crucial to the success of all these ecosystems is one central idea: Code has to not just run in the cloud, it has to be easy to get it there and scale it there. Serverless computing \u2013 calling AWS Lambda functions instead of managing heavyweight applications on infrastructure \u2013 is changing how developers think about backends, event-driving processing, and application design. Infrastructure, deployment, and software platform setup that used to take days or weeks of time vanishes, replaced by microservices that do one thing well, require zero effort to deploy, and scale automatically and implicitly just by using them. At the same time, AWS Lambda and other serverless systems have redefined cloud economics by eliminating the possibility of cold servers, creating a radical new price point for applications running in the cloud and freeing developers and COO\u2019s alike from worrying about paying for unused capacity. In this talk we\u2019ll define Serverless computing, examine the key trends and innovative ideas behind the technology, and look in detail at design patterns for big data, event processing, mobile backends, and more using AWS Lambda."
    :video/url "https://vimeo.com/175888220"
    :video/upload_date "2016-07-22 17:01:04"
    :video/thumbnail_small "https://i.vimeocdn.com/video/583056926_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/583056926_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/583056926_640.webp"
    :video/stats_number_of_likes 1
    :video/stats_number_of_plays 26
    :video/stats_number_of_comments 0
    :video/duration 3297
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 175867600
    :video/title "From Concurrent to Parallel: Understanding Parallel Stream Performance in Java SE 8"
    :video/description "As core counts continue to increase, how we exploit hardware parallelism in practice shifts from concurrency \u2014 using more cores to handle more user requests \u2014 to parallelism \u2014 using more cores to solve data-intensive problems faster. This talk will explore the different goals, tools, and techniques involved between these various approaches, and how to analyze a computation for potential parallelism, with specific attention to the parallel stream library in Java 8."
    :video/url "https://vimeo.com/175867600"
    :video/upload_date "2016-07-22 14:15:58"
    :video/thumbnail_small "https://i.vimeocdn.com/video/583028871_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/583028871_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/583028871_640.webp"
    :video/stats_number_of_likes 2
    :video/stats_number_of_plays 58
    :video/stats_number_of_comments 0
    :video/duration 3642
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 175790877
    :video/title "React Native: A Better Way to Do Mobile (For Both Managers and Engineers)"
    :video/description "In 2015, two years after its initial open source release, React took the position formerly held by Angular as the darling of the web. It\u2019s used on some of the biggest sites in the world, such as Facebook, WhatsApp, Messenger, Instagram, Netflix, Airbnb, Uber, NFL, Dropbox, Asana, Atlassian, Khan Academy, Flipkart, Imgur, Reddit, Paypal, WalMart, WordPress, Wix, SquareSpace, etc.<br />\r\n<br />\r\nLet\u2019s be clear though: any UI you can build with React you can also build without React. React\u2019s value proposition is that it simplifies your UI code, making it easier to build and maintain: it is declarative, component-based, uses one-way data flow, and has an API that most developers can become productive with in an afternoon. The people at Facebook have had so much success with it on the web that they thought \u2013 hey, wouldn\u2019t it be great if we could write native mobile apps like this too? And so React Native was born, and the mobile development landscape will never look the same again."
    :video/url "https://vimeo.com/175790877"
    :video/upload_date "2016-07-22 00:53:59"
    :video/thumbnail_small "https://i.vimeocdn.com/video/582936103_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/582936103_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/582936103_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 13
    :video/stats_number_of_comments 0
    :video/duration 3684
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 175785850
    :video/title "Securing Software by Construction"
    :video/description "The high-profile attacks and data-breaches of the last few years have shown us the importance of securing our software. While it is good that we are seeing more tools that can analyze systems for vulnerabilities, this does not help the programmer write secure code in the first place. To prevent security from becoming a bottleneck\u2013and expensive security mistakes from becoming increasingly probable\u2013we need to look to techniques that allow us to secure software by construction.<br />\r\n<br />\r\nThis talk has two parts. First, I will present technical ideas from research, including my own, that help secure software by construction. Even though these are reasonable ideas, however, the gap between academia and industry often prevents these ideas from becoming realized in practice. Second, I will discuss what prevents longer-term security solutions from being commercialized, how we started the Cybersecurity Factory accelerator bridge the research/industry gap, and how we can work together to address the issues that remain."
    :video/url "https://vimeo.com/175785850"
    :video/upload_date "2016-07-21 23:31:12"
    :video/thumbnail_small "https://i.vimeocdn.com/video/582928892_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/582928892_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/582928892_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 50
    :video/stats_number_of_comments 0
    :video/duration 3234
    :video/width 1920
    :video/height 1080
    :video/tags ""
    :video/embed_privacy "anywhere"},
   {:video/id 174727197
    :video/title "Getting started with N|Solid 1.4.0: Profiling Node.js applications like a boss!"
    :video/description "N|Solid is a profiler for server-side JavaScript code running on Node.js.  It is implemented as a data collection service, known as the N|Solid Hub or Proxy server, a console, and an etcd server.  Your applications connect and send telemetry to the server using a special version of Node.js and some environment variables. See how you can use N|Solid to profile your applications and view statistics from both N|Solid itself as well as the Chrome developer tools."
    :video/url "https://vimeo.com/174727197"
    :video/upload_date "2016-07-14 16:51:12"
    :video/thumbnail_small "https://i.vimeocdn.com/video/581744719_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/581744719_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/581744719_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 41
    :video/stats_number_of_comments 0
    :video/duration 1071
    :video/width 1152
    :video/height 720
    :video/tags "N|Solid, Node.js"
    :video/embed_privacy "anywhere"},
   {:video/id 173844151
    :video/title "The World of Swift 3"
    :video/description "When Apple open sourced Swift late last year, they invited the community into the discussion of where Swift should go and why. Instead of us having to imagine what the Swift language and library stewards and architects are thinking, we can read their words on the Swift evolution mailing list. In this talk we\u2019ll look at what idiomatic Swift will look like soon when Swift 3 is soon released and talk about the reasoning behind some of the choices."
    :video/url "https://vimeo.com/173844151"
    :video/upload_date "2016-07-07 20:16:03"
    :video/thumbnail_small "https://i.vimeocdn.com/video/580385782_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/580385782_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/580385782_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 37
    :video/stats_number_of_comments 0
    :video/duration 2537
    :video/width 1920
    :video/height 1080
    :video/tags "ETE2016:Daniel Steinberg"
    :video/embed_privacy "anywhere"},
   {:video/id 172442541
    :video/title "Taming the Wild Wild West of Next-Gen Front-End Apps"
    :video/description "With the recent release of Angular 2 and React.js capturing growing interest, there are now SO many options to build a front-end to our web applications. Along with the increasing number of developers and the explosive popularity of JavaScript, what was the wild wild west of app development is maturing with it\u2019s own best practices and idioms of software. In this talk we\u2019re casting a wide-net on the range of possibilities for building next-gen front-end apps by looking at the options we have for both building and deploying applications on the edge. Join us as we build and deploy an app in real-time using both Angular 2 and React.js."
    :video/url "https://vimeo.com/172442541"
    :video/upload_date "2016-06-27 12:59:50"
    :video/thumbnail_small "https://i.vimeocdn.com/video/578262679_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/578262679_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/578262679_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 17
    :video/stats_number_of_comments 0
    :video/duration 2965
    :video/width 1920
    :video/height 1080
    :video/tags "ETE2016:Ari Lerner"
    :video/embed_privacy "anywhere"},
   {:video/id 171846495
    :video/title "React.js Reconciliation"
    :video/description "React is a library for building user interfaces. Developers specify how an application \u201cshould look\u201d, and React automatically updates the page when the underlying data changes. React is able to do this through a process we call \u201cReconciliation\u201d. In this talk, I\u2019ll describe how reconciliation works within React, and how we use it to enhance both performance and user experience. In addition to being conceptually interesting, understanding the reconciliation process will allow you to better optimize your own applications."
    :video/url "https://vimeo.com/171846495"
    :video/upload_date "2016-06-22 20:14:26"
    :video/thumbnail_small "https://i.vimeocdn.com/video/577409491_100x75.webp"
    :video/thumbnail_medium "https://i.vimeocdn.com/video/577409491_200x150.webp"
    :video/thumbnail_large "https://i.vimeocdn.com/video/577409491_640.webp"
    :video/stats_number_of_likes 0
    :video/stats_number_of_plays 27
    :video/stats_number_of_comments 0
    :video/duration 3067
    :video/width 1920
    :video/height 1080
    :video/tags "ETE2016:Jim Sproch"
    :video/embed_privacy "anywhere"}]
  )
