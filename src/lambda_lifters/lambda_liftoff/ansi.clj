(ns lambda-lifters.lambda-liftoff.ansi)

(def ESC \u001b)

(def CSI (str ESC "["))

(defn CSI-command [char]
  (fn ([] (str CSI char))
    ([n] (str CSI n char))
    ([n m] (str CSI n ";" m char))))

(def CUU
  "Cursor Up Moves the cursor n (default 1) cells in the given direction.
  If the cursor is already at the edge of the screen, this has no effect."
  (CSI-command \A))

(def CUD "Cursor Down"
  (CSI-command \B))

(def CUF "Cursor Forward"
  (CSI-command \C))

(def CUB "Cursor Back"
  (CSI-command \D))

(def CNL
  "Cursor Next Line Moves cursor to beginning of the line n (default 1) lines down. (not ANSI.SYS)"
  (CSI-command \E))

(def CPL
  "Cursor Previous Line Moves cursor to beginning of the line n (default 1) lines up. (not ANSI.SYS)"
  (CSI-command \F))

(def CHA
  "Cursor Horizontal Absolute Moves the cursor to column n (default 1). (not ANSI.SYS)"
  (CSI-command \G))

(def CUP
  "Cursor Position Moves the cursor to row n, column m.
  The values are 1-based, and default to 1 (top left corner) if omitted.
  A sequence such as CSI ;5H is a synonym for CSI 1;5H as well as CSI 17;H is the same as CSI 17H and CSI 17;1H"
  (CSI-command \H))

(def ED
  "Erase in Display Clears part of the screen.
  If n is 0 (or missing), clear from cursor to end of screen.
  If n is 1, clear from cursor to beginning of the screen.
  If n is 2, clear entire screen (and moves cursor to upper left on DOS ANSI.SYS).
  If n is 3, clear entire screen and delete all lines saved in the scrollback buffer
  (this feature was added for xterm and is supported by other terminal applications)."
  (CSI-command \J))

(def EL
  "Erase in Line Erases part of the line.
  If n is 0 (or missing), clear from cursor to the end of the line.
  If n is 1, clear from cursor to beginning of the line.
  If n is 2, clear entire line.
  Cursor position does not change."
  (CSI-command \K))

(def SU
  "Scroll Up Scroll whole page up by n (default 1) lines.
  New lines are added at the bottom. (not ANSI.SYS)"
  (CSI-command \S))

(def SD
  "Scroll Down Scroll whole page down by n (default 1) lines.
  New lines are added at the top. (not ANSI.SYS)"
  (CSI-command \T))

(def HVP
  "Horizontal Vertical Position Same as CUP, but counts as a format effector function (like CR or LF) rather than an editor function (like CUD or CNL).
  This can lead to different handling in certain terminal modes."
  (CSI-command \f))

;; SGR: "Select Graphic Rendition" styles. This is probably what you came for :-)
(def SGR
  "Select Graphic Rendition Sets colors and style of the characters following this code"
  (CSI-command \m))

(def render-reset "All attributes become turned off" (SGR 0))

(def render-bold
  "Bold or increased intensity
  As with faint, the color change is a PC (SCO / CGA) invention." (SGR 1))
(def render-faint
  "Faint, decreased intensity, or dim
  May be implemented as a light font weight like bold." (SGR 2))
(def render-normal-intensity
  "Normal intensity Neither bold nor faint; color changes where intensity is implemented as such."
  (SGR 22))
(def render-not-bold
  "Normal intensity Neither bold nor faint; color changes where intensity is implemented as such."
  (SGR 22))
(def render-not-faint
  "Normal intensity Neither bold nor faint; color changes where intensity is implemented as such."
  (SGR 22))

(def render-italic "Not widely supported. Sometimes treated as inverse or blink." (SGR 3))
(def render-not-italic "Neither italic, nor blackletter" (SGR 23))

(def render-underline "Style extensions exist for Kitty, VTE, mintty, iTerm2 and Konsole" (SGR 4))
(def render-not-underline "Not underlined Neither singly nor doubly underlined" (SGR 24))

(def render-blink-slow "Sets blinking to less than 150 times per minute" (SGR 5))
(def render-blink-rapid "Rapid blink\tMS-DOS ANSI.SYS, 150+ per minute; not widely supported" (SGR 6))
(def render-not-blink "Not blinking Turn blinking off" (SGR 25))

(def render-reverse "Reverse video or invert\tSwap foreground and background colors; inconsistent emulation" (SGR 7))
(def render-not-reversed "Not reversed" (SGR 27))
(def render-conceal "Conceal or hide\tNot widely supported." (SGR 8))
(def render-not-concealed "Reveal Not concealed" (SGR 28))
(def render-reveal "Reveal Not concealed" (SGR 28))
(def render-strike "Crossed-out, or strike\tCharacters legible but marked as if for deletion. Not supported in Terminal.app." (SGR 9))
(def render-not-strike "Not crossed out" (SGR 29))

(def render-font-primary "Primary (default) font" (SGR 10))
(def render-font-1 "Select alternative font 1" (SGR 11))
(def render-font-2 "Select alternative font 2" (SGR 12))
(def render-font-3 "Select alternative font 3" (SGR 13))
(def render-font-4 "Select alternative font 4" (SGR 14))
(def render-font-5 "Select alternative font 5" (SGR 15))
(def render-font-6 "Select alternative font 6" (SGR 16))
(def render-font-7 "Select alternative font 7" (SGR 17))
(def render-font-8 "Select alternative font 8" (SGR 18))
(def render-font-9 "Select alternative font 9" (SGR 19))
(def render-gothic "Fraktur (Gothic)\tRarely supported" (SGR 20))
(def render-not-blackletter "Neither italic, nor blackletter" (SGR 23))

(def render-underline-double
  "Doubly underlined; or: not bold
  Double-underline per ECMA-48, but instead disables bold intensity on several terminals,
  including in the Linux kernel's console before version 4.17."
  (SGR 21))
(def render-not-underline-double "Not underlined Neither singly nor doubly underlined" (SGR 24))
(def render-proportional-spacing "Proportional spacing ITU T.61 and T.416, not known to be used on terminals" (SGR 26))
(def render-not-proportional-spacing "Disable proportional spacing T.61 and T.416" (SGR 50))

(def render-foreground-black "Set foreground color black" (SGR 30))
(def render-foreground-red "Set foreground color red" (SGR 31))
(def render-foreground-green "Set foreground color green " (SGR 32))
(def render-foreground-yellow "Set foreground color yellow" (SGR 33))
(def render-foreground-blue "Set foreground color blue" (SGR 34))
(def render-foreground-magenta "Set foreground color magenta" (SGR 35))
(def render-foreground-cyan "Set foreground color cyan" (SGR 36))
(def render-foreground-white "Set foreground color white" (SGR 37))
(def render-foreground-rgb-fn "Set foreground color: Next arguments are 2;r;g;b (2 is included)" (partial SGR 38 2))
(def render-foreground-extended-fn
  "Set foreground color: Next arguments are 5;n (5 is included in definition)
  see table https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit"
  (partial SGR 38 5))
(def render-foreground-default "Default foreground color Implementation defined (according to standard)" (SGR 39))

(def render-background-black "Set background color black" (SGR 40))
(def render-background-red "Set background color red" (SGR 41))
(def render-background-green "Set background color green " (SGR 42))
(def render-background-yellow "Set background color yellow" (SGR 43))
(def render-background-blue "Set background color blue" (SGR 44))
(def render-background-magenta "Set background color magenta" (SGR 45))
(def render-background-cyan "Set background color cyan" (SGR 46))
(def render-background-white "Set background color white" (SGR 47))
(def render-background-rgb-fn "Set background color: Next arguments are 2;r;g;b (2 is included)" (partial SGR 48 2))
(def render-background-extended-fn
  "Set background color: Next arguments are 5;n (5 is included in definition)
  see table https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit"
  (partial SGR 48 5))
(def render-background-default "Default background color Implementation defined (according to standard)" (SGR 49))

(def render-framed "Framed Implemented as \"emoji variation selector\" in mintty." (SGR 51))
(def render-encircled "Encircled Implemented as \"emoji variation selector\" in mintty." (SGR 52))
(def render-overlined "Overlined Not supported in Terminal.app" (SGR 53))
(def render-not-framed "Neither framed nor encircled" (SGR 54))
(def render-not-encircled "Neither framed nor encircled" (SGR 54))
(def render-not-overlined "Not overlined" (SGR 55))
(def render-underline-colour-rgb-fn "Set underlined color: Next arguments are 2;r;g;b (2 is included)" (partial SGR 58 2))
(def render-underline-colour-extended-fn
  "Set foreground color: Next arguments are 5;n (5 is included in definition)
  see table https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit"
  (partial SGR 58 5))
(def render-underline-colour-default
  "Default underline color Not in standard; implemented in Kitty, VTE, mintty, and iTerm2."
  (SGR 59))

(def render-superscript "Superscript Implemented only in mintty" (SGR 74))
(def render-subscript "Subscript Implemented only in mintty" (SGR 74))
(def render-not-superscript "Neither superscript nor subscript" (SGR 75))
(def render-not-subscript "Neither superscript nor subscript" (SGR 75))
(def render-not-x_script "Neither superscript nor subscript" (SGR 75))

(def render-foreground-bright-black "Set foreground color bright black" (SGR 90))
(def render-foreground-bright-red "Set foreground color bright red" (SGR 91))
(def render-foreground-bright-green "Set foreground color bright green " (SGR 92))
(def render-foreground-bright-yellow "Set foreground color bright yellow" (SGR 93))
(def render-foreground-bright-blue "Set foreground color bright blue" (SGR 94))
(def render-foreground-bright-magenta "Set foreground color bright magenta" (SGR 95))
(def render-foreground-bright-cyan "Set foreground color bright cyan" (SGR 96))
(def render-foreground-bright-white "Set foreground color bright white" (SGR 97))

(def render-background-bright-black "Set background color bright black" (SGR 100))
(def render-background-bright-red "Set background color bright red" (SGR 101))
(def render-background-bright-green "Set background color bright green " (SGR 102))
(def render-background-bright-yellow "Set background color bright yellow" (SGR 103))
(def render-background-bright-blue "Set background color bright blue" (SGR 104))
(def render-background-bright-magenta "Set background color bright magenta" (SGR 105))
(def render-background-bright-cyan "Set background color bright cyan" (SGR 106))
(def render-background-bright-white "Set background color bright white" (SGR 107))

(defn render-string-fn [open close]
  (fn [& bits] (str open (apply str bits) close)))

;; ---------------------------------------------------------------------------------------------------
(def BOLD (render-string-fn render-bold render-not-bold))
(def FAINT (render-string-fn render-faint render-not-faint))
(def ITALIC (render-string-fn render-italic render-not-italic))
(def UNDERLINE (render-string-fn render-underline render-not-underline))
(def SLOW-BLINK (render-string-fn render-blink-slow render-not-blink))
(def FAST-BLINK (render-string-fn render-blink-rapid render-not-blink))
(def REVERSE (render-string-fn render-reverse render-not-reversed))
(def CONCEAL (render-string-fn render-conceal render-not-concealed))
(def STRIKE (render-string-fn render-strike render-not-strike))
(def DOUBLE-UNDERLINE (render-string-fn render-underline-double render-not-underline-double))

(defn fg-string-fn [colour-sgr] (render-string-fn colour-sgr render-foreground-default))
(defn bg-string-fn [colour-sgr] (render-string-fn colour-sgr render-background-default))

(def FG-BLACK (fg-string-fn render-foreground-black))
(def FG-RED (fg-string-fn render-foreground-red))
(def FG-GREEN (fg-string-fn render-foreground-green))
(def FG-YELLOW (fg-string-fn render-foreground-yellow))
(def FG-BLUE (fg-string-fn render-foreground-blue))
(def FG-MAGENTA (fg-string-fn render-foreground-magenta))
(def FG-CYAN (fg-string-fn render-foreground-cyan))
(def FG-WHITE (fg-string-fn render-foreground-white))

(def BG-BLACK (bg-string-fn render-background-black))
(def BG-RED (bg-string-fn render-background-red))
(def BG-GREEN (bg-string-fn render-background-green))
(def BG-YELLOW (bg-string-fn render-background-yellow))
(def BG-BLUE (bg-string-fn render-background-blue))
(def BG-MAGENTA (bg-string-fn render-background-magenta))
(def BG-CYAN (bg-string-fn render-background-cyan))
(def BG-WHITE (bg-string-fn render-background-white))

(def FG-BRIGHT-BLACK (fg-string-fn render-foreground-bright-black))
(def FG-BRIGHT-RED (fg-string-fn render-foreground-bright-red))
(def FG-BRIGHT-GREEN (fg-string-fn render-foreground-bright-green))
(def FG-BRIGHT-YELLOW (fg-string-fn render-foreground-bright-yellow))
(def FG-BRIGHT-BLUE (fg-string-fn render-foreground-bright-blue))
(def FG-BRIGHT-MAGENTA (fg-string-fn render-foreground-bright-magenta))
(def FG-BRIGHT-CYAN (fg-string-fn render-foreground-bright-cyan))
(def FG-BRIGHT-WHITE (fg-string-fn render-foreground-bright-white))

(def BG-BRIGHT-BLACK (bg-string-fn render-background-bright-black))
(def BG-BRIGHT-RED (bg-string-fn render-background-bright-red))
(def BG-BRIGHT-GREEN (bg-string-fn render-background-bright-green))
(def BG-BRIGHT-YELLOW (bg-string-fn render-background-bright-yellow))
(def BG-BRIGHT-BLUE (bg-string-fn render-background-bright-blue))
(def BG-BRIGHT-MAGENTA (bg-string-fn render-background-bright-magenta))
(def BG-BRIGHT-CYAN (bg-string-fn render-background-bright-cyan))
(def BG-BRIGHT-WHITE (bg-string-fn render-background-bright-white))
