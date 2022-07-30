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

    
    [[31;1mWeb Assembly[m] not found. See tracks for available options.
    
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

    
     ========= [33;1mplsql[m =========
    Downloaded exercise [32;1mhello-world[m
    You have not unlocked exercise [31;1mbinary[m
    You have not unlocked exercise [31;1mdifference-of-squares[m
    You have not unlocked exercise [31;1mgigasecond[m
    You have not unlocked exercise [31;1mgrains[m
    You have not unlocked exercise [31;1mhamming[m
    You have not unlocked exercise [31;1mleap[m
    You have not unlocked exercise [31;1mnth-prime[m
    You have not unlocked exercise [31;1mraindrops[m
    You have not unlocked exercise [31;1mrna-transcription[m
    You have not unlocked exercise [31;1mroman-numerals[m
    
     ========= [33;1mclojure[m =========
    Downloaded exercise [32;1mhello-world[m
    Downloaded exercise [32;1mlucians-luscious-lasagna[m
    Downloaded exercise [32;1mtracks-on-tracks-on-tracks[m
    Downloaded exercise [32;1mbird-watcher[m
    Downloaded exercise [32;1mcars-assemble[m
    Downloaded exercise [32;1minterest-is-interesting[m
    Downloaded exercise [32;1mannalyns-infiltration[m
    Downloaded exercise [32;1mlog-levels[m
    Downloaded exercise [32;1melyses-destructured-enchantments[m
    Downloaded exercise [32;1mtwo-fer[m
    Downloaded exercise [32;1marmstrong-numbers[m
    Downloaded exercise [32;1mreverse-string[m
    Downloaded exercise [32;1maccumulate[m
    Downloaded exercise [32;1macronym[m
    Downloaded exercise [32;1mall-your-base[m
    Downloaded exercise [32;1managram[m
    Downloaded exercise [32;1mbob[m
    Downloaded exercise [32;1mcollatz-conjecture[m
    Downloaded exercise [32;1mcomplex-numbers[m
    Downloaded exercise [32;1metl[m
    Downloaded exercise [32;1mhamming[m
    Downloaded exercise [32;1mnth-prime[m
    Downloaded exercise [32;1mnucleotide-count[m
    Downloaded exercise [32;1mpangram[m
    Downloaded exercise [32;1mpig-latin[m
    Downloaded exercise [32;1mprotein-translation[m
    Downloaded exercise [32;1mraindrops[m
    Downloaded exercise [32;1mrna-transcription[m
    Downloaded exercise [32;1mrobot-name[m
    Downloaded exercise [32;1mroman-numerals[m
    Downloaded exercise [32;1mrotational-cipher[m
    Downloaded exercise [32;1mrun-length-encoding[m
    Downloaded exercise [32;1mscrabble-score[m
    Downloaded exercise [32;1msecret-handshake[m
    Downloaded exercise [32;1mseries[m
    Downloaded exercise [32;1mspace-age[m
    Downloaded exercise [32;1mstrain[m
    Downloaded exercise [32;1msublist[m
    Downloaded exercise [32;1msum-of-multiples[m
    Downloaded exercise [32;1mtriangle[m
    Downloaded exercise [32;1mword-count[m
    Downloaded exercise [32;1matbash-cipher[m
    Downloaded exercise [32;1mbeer-song[m
    Downloaded exercise [32;1mbinary[m
    Downloaded exercise [32;1mbinary-search[m
    Downloaded exercise [32;1mbinary-search-tree[m
    Downloaded exercise [32;1mchange[m
    Downloaded exercise [32;1mflatten-array[m
    Downloaded exercise [32;1mgigasecond[m
    Downloaded exercise [32;1mgrade-school[m
    Downloaded exercise [32;1mgrains[m
    Downloaded exercise [32;1mhexadecimal[m
    Downloaded exercise [32;1misbn-verifier[m
    Downloaded exercise [32;1misogram[m
    Downloaded exercise [32;1mkindergarten-garden[m
    Downloaded exercise [32;1mleap[m
    Downloaded exercise [32;1mpascals-triangle[m
    Downloaded exercise [32;1mperfect-numbers[m
    Downloaded exercise [32;1mphone-number[m
    Downloaded exercise [32;1mprime-factors[m
    Downloaded exercise [32;1mproverb[m
    Downloaded exercise [32;1msay[m
    Downloaded exercise [32;1mtrinary[m
    Downloaded exercise [32;1mallergies[m
    Downloaded exercise [32;1mcrypto-square[m
    Downloaded exercise [32;1mdifference-of-squares[m
    Downloaded exercise [32;1mdominoes[m
    Downloaded exercise [32;1mlargest-series-product[m
    Downloaded exercise [32;1mmeetup[m
    Downloaded exercise [32;1moctal[m
    Downloaded exercise [32;1mspiral-matrix[m
    Downloaded exercise [32;1mclock[m
    Downloaded exercise [32;1mdiamond[m
    Downloaded exercise [32;1mluhn[m
    Downloaded exercise [32;1msieve[m
    Downloaded exercise [32;1mrobot-simulator[m
    Downloaded exercise [32;1mwordy[m
    Downloaded exercise [32;1mbank-account[m
    Downloaded exercise [32;1mmatching-brackets[m
    Downloaded exercise [32;1mminesweeper[m
    Downloaded exercise [32;1mpoker[m
    Downloaded exercise [32;1mqueen-attack[m
    Downloaded exercise [32;1mgo-counting[m
    Downloaded exercise [32;1mpov[m
    


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
