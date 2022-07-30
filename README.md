# exercism-track-downloader

`exercism-track-downloader` is a small Clojure (Java) CLI application to download all exercises for track(s) from [exercism.org](https://exercism.org/).

```sh
exercism-track-downloader.jar [args]
```

Examples are provided in the [usage](#usage) section.

## Pre-requisities

+ [Exercim's CLI](https://exercism.org/docs/using/solving-exercises/working-locally) application must be installed as it used to retrieve the exercises.

+ Exercises must be *unlocked* to be *downloaded*. Locked exercises cannot be downloaded.

## Installation

Copy the `target/uberjar/exercism-track-downloader-<version>-standalone.jar` file to somewhere in your `PATH` and give it executable permissions.

Alternatively, Clojure developers can use the code or library  (`target/uberjar/exercism-track-downloader-<version>.jar`) directly.


## Usage

Execute commands below from within the root directory. Copy-paste them to your terminal and remove the leading `!` character. Note that `exercism-track-downloader.jar` is symlinked to `target/uberjar/exercism-track-downloader-1.0-standalone.jar`.

> _Note_: All data is retrieved directly from [exercism.org](https://exercism.org/) on each run

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

    
     ========= plsql =========
    Downloaded exercise hello-world
    You have not unlocked exercise binary
    You have not unlocked exercise difference-of-squares
    You have not unlocked exercise gigasecond
    You have not unlocked exercise grains
    You have not unlocked exercise hamming
    You have not unlocked exercise leap
    You have not unlocked exercise nth-prime
    You have not unlocked exercise raindrops
    You have not unlocked exercise rna-transcription
    You have not unlocked exercise roman-numerals
    
     ========= clojure =========
    Downloaded exercise hello-world
    Downloaded exercise lucians-luscious-lasagna
    Downloaded exercise tracks-on-tracks-on-tracks
    Downloaded exercise bird-watcher
    Downloaded exercise cars-assemble
    Downloaded exercise interest-is-interesting
    Downloaded exercise annalyns-infiltration
    Downloaded exercise log-levels
    Downloaded exercise elyses-destructured-enchantments
    Downloaded exercise two-fer
    Downloaded exercise armstrong-numbers
    Downloaded exercise reverse-string
    Downloaded exercise accumulate
    Downloaded exercise acronym
    Downloaded exercise all-your-base
    Downloaded exercise anagram
    Downloaded exercise bob
    Downloaded exercise collatz-conjecture
    Downloaded exercise complex-numbers
    Downloaded exercise etl
    Downloaded exercise hamming
    Downloaded exercise nth-prime
    Downloaded exercise nucleotide-count
    Downloaded exercise pangram
    Downloaded exercise pig-latin
    Downloaded exercise protein-translation
    Downloaded exercise raindrops
    Downloaded exercise rna-transcription
    Downloaded exercise robot-name
    Downloaded exercise roman-numerals
    Downloaded exercise rotational-cipher
    Downloaded exercise run-length-encoding
    Downloaded exercise scrabble-score
    Downloaded exercise secret-handshake
    Downloaded exercise series
    Downloaded exercise space-age
    Downloaded exercise strain
    Downloaded exercise sublist
    Downloaded exercise sum-of-multiples
    Downloaded exercise triangle
    Downloaded exercise word-count
    Downloaded exercise atbash-cipher
    Downloaded exercise beer-song
    Downloaded exercise binary
    Downloaded exercise binary-search
    Downloaded exercise binary-search-tree
    Downloaded exercise change
    Downloaded exercise flatten-array
    Downloaded exercise gigasecond
    Downloaded exercise grade-school
    Downloaded exercise grains
    Downloaded exercise hexadecimal
    Downloaded exercise isbn-verifier
    Downloaded exercise isogram
    Downloaded exercise kindergarten-garden
    Downloaded exercise leap
    Downloaded exercise pascals-triangle
    Downloaded exercise perfect-numbers
    Downloaded exercise phone-number
    Downloaded exercise prime-factors
    Downloaded exercise proverb
    Downloaded exercise say
    Downloaded exercise trinary
    Downloaded exercise allergies
    Downloaded exercise crypto-square
    Downloaded exercise difference-of-squares
    Downloaded exercise dominoes
    Downloaded exercise largest-series-product
    Downloaded exercise meetup
    Downloaded exercise octal
    Downloaded exercise spiral-matrix
    Downloaded exercise clock
    Downloaded exercise diamond
    Downloaded exercise luhn
    Downloaded exercise sieve
    Downloaded exercise robot-simulator
    Downloaded exercise wordy
    Downloaded exercise bank-account
    Downloaded exercise matching-brackets
    Downloaded exercise minesweeper
    Downloaded exercise poker
    Downloaded exercise queen-attack
    Downloaded exercise go-counting
    Downloaded exercise pov
    


## Speeding up Execution

### (Slow) Time of Standalone Executable

The startup time is slow due to Clojure's [slow loading time](http://clojure-goes-fast.com/blog/clojures-slow-start/#:~:text=Clojure%20projects%20are%20slow%20to,the%20classes%20are%20loaded%20slowly.). The execution time might be also be slow as data is retrieved directly from exercism.org and parsed every time the application is run.


```python
!time java -jar exercism-track-downloader.jar tracks --search "^.*c"
```

    
    Retrieving tracks from https://exercism.io/tracks
    
    
    |       :slug |       :lang |
    |-------------+-------------|
    |           c |           C |
    | objective-c | Objective-C |
    
    java -jar exercism-track-downloader.jar tracks --search "^.*c"  5.06s user 0.27s system 118% cpu 4.512 total



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
    
    java -jar exercism-track-downloader.jar exercises --list plsql  5.69s user 0.28s system 128% cpu 4.638 total


### Drip

One option to significantly increase the speed of startup is by using an application like [drip](https://github.com/ninjudd/drip). 

```bash
!time drip -jar exercism-track-downloader.jar tracks --search "^.*c"
```

```
Retrieving tracks from https://exercism.io/tracks


|       :slug |       :lang |
|-------------+-------------|
|           c |           C |
| objective-c | Objective-C |

drip -jar exercism-track-downloader.jar tracks --search "^.*c"  0.06s user 0.08s system 61% cpu 0.225 total
```

```bash
!time drip -jar exercism-track-downloader.jar exercises --list plsql
```

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

drip -jar exercism-track-downloader.jar exercises --list plsql  0.06s user 0.08s system 15% cpu 0.879 total
```
