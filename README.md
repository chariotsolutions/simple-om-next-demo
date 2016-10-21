<nav id="table-of-contents">
<h2>Table of Contents</h2>
<div id="text-table-of-contents">
<ul>
<li><a href="#orgheadline1">1. README for OmNext-Demo application</a></li>
</ul>
</div>
</nav>

# README for OmNext-Demo application<a id="orgheadline1"></a>

This is an Om.Next application inspired by Ken Rimple's [React
presentation for the LibertyJS](https://github.com/krimple/libertyjs-react-talk-public) group.

A number of changes have been introduced.

-   Instead of fetching the video information from the *Vimeo* catalog
    for the `chariotsolutions` user, the data is hard-coded in
    `omnext-demo.video-catalog`. It should be possible to update the
    `vimeo-remote` operation to fetch the data directly.
-   Ken Rimple's original architecture created separate *React*
    components for *container* and *component* elements. This was
    derived from a source I don't remember at the moment, and was
    motivated by a desire to separate the management of state (by the
    container) and the display of state (by the visual component).
    Since in an *Om Next* app the state is managed through `transact!`
    calls, this separation did not seem helpful, and the containers
    and components have been consolidated. (This sometimes left a lot
    of `div` elements: sorry.)
-   The `MediaItem` elements have gone through several evolutions:
    -   first, they changed visual state when selected;
    -   next, they displayed related videos (in order to test
        deeply-nested queries);
    -   now, they fill rows horizontally, showing a thumbnail of the
        video.
-   The default build uses [Bruce Hauman](https://github.com/bhauman)'s [`devcards`](https://github.com/bhauman/devcards), which gave me a
    framework for quickly checking out how components work (or
    didn't, as they often did when things got complex. See his
    [Literate interactive coding: Devcards](https://www.youtube.com/watch?v=G7Z_g2fnEDg) video.
    
    By the way, I probably used *Devcards* in a naive way: I should
    have had a separate module for each component I used. Maybe later.
    
    And it looks (from the video) that I should have used [Ŝablono](https://github.com/r0man/sablono) in
    my devcards. Sigh. Later.
-   The [same video](https://www.youtube.com/watch?v=G7Z_g2fnEDg) also demonstrates the value of Hauman's [Figwheel](https://github.com/bhauman/lein-figwheel),
    which is awesome beyond measure.

I believe that most other changes come from adapting the code to *Om
Next*, or a irresistible desire to tinker with what I had running.
