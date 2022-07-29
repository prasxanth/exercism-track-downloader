# exercism-track-downloader

`exercism-track-downloader` is a small Clojure (Java) CLI application to download all exercises for track(s) from [exercism.org](https://exercism.org/).

```sh
exercism-track-downloader.jar [args]
```

Examples are provided in the [usage](#usage) section.

## Pre-requisities

+ [Exercim's CLI](https://exercism.org/docs/using/solving-exercises/working-locally) application must be installed as it used to retrieve the exercises.

+ Exercises must be *unlocked* to be *downloaded*. Locked exercises cannot be downloaded.

+ All data is retrieved directly from website on each run so an internet connection is required.

## Installation

Copy the `target/uberjar/exercism-track-downloader-<version>-standalone.jar` file to somewhere in your `PATH` and give it executable permissions.

Alternatively, Clojure developers can use the code or library  (`target/uberjar/exercism-track-downloader-<version>.jar`) directly.


## Usage

Execute commands below from within the root directory. Copy-paste them to your terminal and remove the leading `!` character. Note that `exercism-track-downloader.jar` is symlinked to `target/uberjar/exercism-track-downloader-1.0-standalone.jar`.

### Help


```python
!java -jar exercism-track-downloader.jar --help
```

    NAME:
     exercism-track-downloader - Application to download all exercises from Exercism for any track(s).
    
    USAGE:
     exercism-track-downloader [global-options] command [command options] [arguments...]
    
    VERSION:
     1.0
    
    COMMANDS:
       tracks               List available tracks on Exercism
       exercises            List and download exercises for each track
    
    GLOBAL OPTIONS:
       -?, --help
    


### Tracks

#### Help


```python
!java -jar exercism-track-downloader.jar tracks --help
```

    NAME:
     exercism-track-downloader tracks - List available tracks on Exercism
    
    USAGE:
     exercism-track-downloader tracks [command options] [arguments...]
    
    EXAMPLES:
     exercism-track-downloader tracks --list
     exercism-track-downloader tracks --slugs
     exercism-track-downloader tracks --langs
     exercism-track-downloader tracks --search ".*L$"
    
    OPTIONS:
       -l, --[no-]list   List both track slugs and language mappings
       -s, --[no-]slugs  List track slugs only
       -g, --[no-]langs  List track languages only
       -r, --search S    Search available track listings using regex
       -?, --help
    


#### Listings


```python
!java -jar exercism-track-downloader.jar tracks --list
```

    
    Retrieving tracks from https://exercism.io/tracks
    
    
    |           :slug |           :lang |
    |-----------------+-----------------|
    |            abap |            ABAP |
    |             awk |             AWK |
    |            bash |            Bash |
    |               c |               C |
    |            cfml |            CFML |
    |         clojure |         Clojure |
    |   clojurescript |   ClojureScript |
    |    coffeescript |    CoffeeScript |
    |     common-lisp |     Common Lisp |
    |             cpp |             C++ |
    |         crystal |         Crystal |
    |          csharp |              C# |
    |               d |               D |
    |            dart |            Dart |
    |          delphi |   Delphi Pascal |
    |          elixir |          Elixir |
    |             elm |             Elm |
    |      emacs-lisp |      Emacs Lisp |
    |          erlang |          Erlang |
    |         fortran |         Fortran |
    |          fsharp |              F# |
    |              go |              Go |
    |          groovy |          Groovy |
    |         haskell |         Haskell |
    |            java |            Java |
    |      javascript |      JavaScript |
    |           julia |           Julia |
    |          kotlin |          Kotlin |
    |             lfe |             LFE |
    |             lua |             Lua |
    |            mips |   MIPS Assembly |
    |             nim |             Nim |
    |     objective-c |     Objective-C |
    |           ocaml |           OCaml |
    |           perl5 |          Perl 5 |
    | pharo-smalltalk |           Pharo |
    |             php |             PHP |
    |           plsql |          PL/SQL |
    |          prolog |          Prolog |
    |      purescript |      PureScript |
    |          python |          Python |
    |               r |               R |
    |          racket |          Racket |
    |            raku |            Raku |
    |        reasonml |        ReasonML |
    |            ruby |            Ruby |
    |            rust |            Rust |
    |           scala |           Scala |
    |          scheme |          Scheme |
    |             sml |     Standard ML |
    |           swift |           Swift |
    |             tcl |             Tcl |
    |      typescript |      TypeScript |
    |          unison |          Unison |
    |           vbnet |          VB.NET |
    |       vimscript |      Vim script |
    |            wasm |     WebAssembly |
    |            wren |            Wren |
    | x86-64-assembly | x86-64 Assembly |
    


#### Slugs (URL abbreviations) Only


```python
!java -jar exercism-track-downloader.jar tracks --slugs | grep "^c.*"
```

    c
    cfml
    clojure
    clojurescript
    coffeescript
    common-lisp
    cpp
    crystal
    csharp


#### Languages Only


```python
!java -jar exercism-track-downloader.jar tracks --langs | grep ".*ML.*"
```

    CFML
    ReasonML
    Standard ML


#### Search Track Listings


```python
!java -jar exercism-track-downloader.jar tracks --search ".*L$"
```

    
    Retrieving tracks from https://exercism.io/tracks
    
    
    |    :slug |       :lang |
    |----------+-------------|
    |     cfml |        CFML |
    |    plsql |      PL/SQL |
    | reasonml |    ReasonML |
    |      sml | Standard ML |
    


### Exercises

#### Help


```python
!java -jar exercism-track-downloader.jar exercises --help
```

    NAME:
     exercism-track-downloader exercises - List and download exercises for each track
    
    USAGE:
     exercism-track-downloader exercises [command options] [arguments...]
    
    EXAMPLES:
     exercism-track-downloader exercises --list plsql -l clojure
     exercism-track-downloader exercises --download wasm -d awk
    
    OPTIONS:
       -l, --list S      List exercises for target tracks
       -d, --download S  Download exercises for target tracks
       -?, --help
    


#### Listings


```python
!java -jar exercism-track-downloader.jar exercises --list plsql -l WebAssembly
```

    ========= plsql =========
    
    |                :title | :difficulty |    :type |
    |-----------------------+-------------+----------|
    |           Hello World |        easy | tutorial |
    |                Binary |        easy | practice |
    | Difference Of Squares |        easy | practice |
    |            Gigasecond |        easy | practice |
    |                Grains |        easy | practice |
    |               Hamming |        easy | practice |
    |                  Leap |        easy | practice |
    |             Nth Prime |        easy | practice |
    |             Raindrops |        easy | practice |
    |     Rna Transcription |        easy | practice |
    |        Roman Numerals |        easy | practice |
    
    ========= wasm =========
    
    |                :title | :difficulty |    :type |
    |-----------------------+-------------+----------|
    |           Hello World |        easy | tutorial |
    |                 Darts |        easy | practice |
    |    Collatz Conjecture |        easy | practice |
    |                Grains |      medium | practice |
    | Difference of Squares |        easy | practice |
    |                  Leap |        easy | practice |
    |        Resistor Color |      medium | practice |
    |        Reverse String |        easy | practice |
    |               Two Fer |        easy | practice |
    |          Bank Account |        easy | practice |
    |       Circular Buffer |        hard | practice |
    |               Acronym |        easy | practice |
    |         All Your Base |      medium | practice |
    |         Binary Search |      medium | practice |
    |              Triangle |        easy | practice |
    |      Nucleotide Count |        easy | practice |
    |     Armstrong Numbers |        easy | practice |
    |     Rna Transcription |        easy | practice |
    |             Raindrops |        easy | practice |
    |               Hamming |        easy | practice |
    |               Pangram |        easy | practice |
    


If one ore more tracks are not found it is skipped and a message is displayed


```python
!java -jar exercism-track-downloader.jar exercises --list plsql -l "Web Assembly"
```

    
    [Web Assembly] not found. See tracks for available options.
    
    ========= plsql =========
    
    |                :title | :difficulty |    :type |
    |-----------------------+-------------+----------|
    |           Hello World |        easy | tutorial |
    |                Binary |        easy | practice |
    | Difference Of Squares |        easy | practice |
    |            Gigasecond |        easy | practice |
    |                Grains |        easy | practice |
    |               Hamming |        easy | practice |
    |                  Leap |        easy | practice |
    |             Nth Prime |        easy | practice |
    |             Raindrops |        easy | practice |
    |     Rna Transcription |        easy | practice |
    |        Roman Numerals |        easy | practice |
    


## Download!

> Note that only unlocked exercises can be downloaded.


```python
!java -jar exercism-track-downloader.jar exercises --download plsql -d clojure
```

    
    Downloaded exercise <hello-world> for track <plsql>
    You have not unlocked exercise <binary> for track <plsql>
    You have not unlocked exercise <difference-of-squares> for track <plsql>
    You have not unlocked exercise <gigasecond> for track <plsql>
    You have not unlocked exercise <grains> for track <plsql>
    You have not unlocked exercise <hamming> for track <plsql>
    You have not unlocked exercise <leap> for track <plsql>
    You have not unlocked exercise <nth-prime> for track <plsql>
    You have not unlocked exercise <raindrops> for track <plsql>
    You have not unlocked exercise <rna-transcription> for track <plsql>
    You have not unlocked exercise <roman-numerals> for track <plsql>
    Downloaded exercise <hello-world> for track <clojure>
    Downloaded exercise <lucians-luscious-lasagna> for track <clojure>
    Downloaded exercise <tracks-on-tracks-on-tracks> for track <clojure>
    Downloaded exercise <bird-watcher> for track <clojure>
    Downloaded exercise <cars-assemble> for track <clojure>
    Downloaded exercise <interest-is-interesting> for track <clojure>
    Downloaded exercise <annalyns-infiltration> for track <clojure>
    Downloaded exercise <log-levels> for track <clojure>
    Downloaded exercise <elyses-destructured-enchantments> for track <clojure>
    Downloaded exercise <two-fer> for track <clojure>
    Downloaded exercise <armstrong-numbers> for track <clojure>
    Downloaded exercise <reverse-string> for track <clojure>
    Downloaded exercise <accumulate> for track <clojure>
    Downloaded exercise <acronym> for track <clojure>
    Downloaded exercise <all-your-base> for track <clojure>
    Downloaded exercise <anagram> for track <clojure>
    Downloaded exercise <bob> for track <clojure>
    Downloaded exercise <collatz-conjecture> for track <clojure>
    Downloaded exercise <complex-numbers> for track <clojure>
    Downloaded exercise <etl> for track <clojure>
    Downloaded exercise <hamming> for track <clojure>
    Downloaded exercise <nth-prime> for track <clojure>
    Downloaded exercise <nucleotide-count> for track <clojure>
    Downloaded exercise <pangram> for track <clojure>
    Downloaded exercise <pig-latin> for track <clojure>
    Downloaded exercise <protein-translation> for track <clojure>
    Downloaded exercise <raindrops> for track <clojure>
    Downloaded exercise <rna-transcription> for track <clojure>
    Downloaded exercise <robot-name> for track <clojure>
    Downloaded exercise <roman-numerals> for track <clojure>
    Downloaded exercise <rotational-cipher> for track <clojure>
    Downloaded exercise <run-length-encoding> for track <clojure>
    Downloaded exercise <scrabble-score> for track <clojure>
    Downloaded exercise <secret-handshake> for track <clojure>
    Downloaded exercise <series> for track <clojure>
    Downloaded exercise <space-age> for track <clojure>
    Downloaded exercise <strain> for track <clojure>
    Downloaded exercise <sublist> for track <clojure>
    Downloaded exercise <sum-of-multiples> for track <clojure>
    Downloaded exercise <triangle> for track <clojure>
    Downloaded exercise <word-count> for track <clojure>
    Downloaded exercise <atbash-cipher> for track <clojure>
    Downloaded exercise <beer-song> for track <clojure>
    Downloaded exercise <binary> for track <clojure>
    Downloaded exercise <binary-search> for track <clojure>
    Downloaded exercise <binary-search-tree> for track <clojure>
    Downloaded exercise <change> for track <clojure>
    Downloaded exercise <flatten-array> for track <clojure>
    Downloaded exercise <gigasecond> for track <clojure>
    Downloaded exercise <grade-school> for track <clojure>
    Downloaded exercise <grains> for track <clojure>
    Downloaded exercise <hexadecimal> for track <clojure>
    Downloaded exercise <isbn-verifier> for track <clojure>
    Downloaded exercise <isogram> for track <clojure>
    Downloaded exercise <kindergarten-garden> for track <clojure>
    Downloaded exercise <leap> for track <clojure>
    Downloaded exercise <pascals-triangle> for track <clojure>
    Downloaded exercise <perfect-numbers> for track <clojure>
    Downloaded exercise <phone-number> for track <clojure>
    Downloaded exercise <prime-factors> for track <clojure>
    Downloaded exercise <proverb> for track <clojure>
    Downloaded exercise <say> for track <clojure>
    Downloaded exercise <trinary> for track <clojure>
    Downloaded exercise <allergies> for track <clojure>
    Downloaded exercise <crypto-square> for track <clojure>
    Downloaded exercise <difference-of-squares> for track <clojure>
    Downloaded exercise <dominoes> for track <clojure>
    Downloaded exercise <largest-series-product> for track <clojure>
    Downloaded exercise <meetup> for track <clojure>
    Downloaded exercise <octal> for track <clojure>
    Downloaded exercise <spiral-matrix> for track <clojure>
    Downloaded exercise <clock> for track <clojure>
    Downloaded exercise <diamond> for track <clojure>
    Downloaded exercise <luhn> for track <clojure>
    Downloaded exercise <sieve> for track <clojure>
    Downloaded exercise <robot-simulator> for track <clojure>
    Downloaded exercise <wordy> for track <clojure>
    Downloaded exercise <bank-account> for track <clojure>
    Downloaded exercise <matching-brackets> for track <clojure>
    Downloaded exercise <minesweeper> for track <clojure>
    Downloaded exercise <poker> for track <clojure>
    Downloaded exercise <queen-attack> for track <clojure>
    Downloaded exercise <go-counting> for track <clojure>
    Downloaded exercise <pov> for track <clojure>
    


## Warning!

The execution time is slow partly due to Clojure's [slow loading time](http://clojure-goes-fast.com/blog/clojures-slow-start/#:~:text=Clojure%20projects%20are%20slow%20to,the%20classes%20are%20loaded%20slowly.) and partly because data is retrieved directly from exercism.org and parsed every time the application is run.


```python
!time java -jar exercism-track-downloader.jar tracks --search "^.*c"
```

    
    Retrieving tracks from https://exercism.io/tracks
    
    
    |       :slug |       :lang |
    |-------------+-------------|
    |           c |           C |
    | objective-c | Objective-C |
    
    java -jar exercism-track-downloader.jar tracks --search "^.*c"  4.71s user 0.28s system 121% cpu 4.119 total



```python
!time java -jar exercism-track-downloader.jar exercises --list plsql
```

    ========= plsql =========
    
    |                :title | :difficulty |    :type |
    |-----------------------+-------------+----------|
    |           Hello World |        easy | tutorial |
    |                Binary |        easy | practice |
    | Difference Of Squares |        easy | practice |
    |            Gigasecond |        easy | practice |
    |                Grains |        easy | practice |
    |               Hamming |        easy | practice |
    |                  Leap |        easy | practice |
    |             Nth Prime |        easy | practice |
    |             Raindrops |        easy | practice |
    |     Rna Transcription |        easy | practice |
    |        Roman Numerals |        easy | practice |
    
    java -jar exercism-track-downloader.jar exercises --list plsql  5.13s user 0.28s system 108% cpu 4.971 total

