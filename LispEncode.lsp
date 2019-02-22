; Gray Meeks
; CSCI 530 Assignment 3 Part 1
;
; Source(s):
;
; http://cl-cookbook.sourceforge.net/strings.html [String manipulation]
; lisp_examples.lsp [BB / general purpose helper functions]

; charListToString returns a string based
; on a given list of characters
(defun charListToString(char_list)
    (coerce char_list 'string))

; stringToCharList returns a list of
; characters based on a given string
(defun stringToCharList(str)
    (coerce str 'list))

; Returns a character list based on a
; given list of ascii integers
(defun intListToCharList (int_list)
    (if int_list
        (cons (intToChar (car int_list)) (intListToCharList (cdr int_list)))))

; Returns an int list with corresponding
; ascii values to a given char list
(defun charListToIntList (char_list)
    (if char_list
        (cons (charToInt (car char_list)) (charListToIntList (cdr char_list)))))
    
; Returns length of a given list
; Source: lisp example file on BB
(defun listLen (list)
	(if list
		(+ 1 (listLen (cdr list)));true
		0));false - end of my list

; Returns an integer value for
; a given character
(defun charToInt (c)
    (char-code c))

; Returns a character for
; a given integer
(defun intToChar (num)
    (code-char num))

; Subtracts a given number by
; twenty six until a value
; less than 123 is reached
(defun twentySix(num)
    (if (< num 123)
        num
        (twentySix (- num 26))))

; Performs the inverse of the
; twentySix function
(defun twentySixInverse(num)
    (if (> num 96)
        num
        (twentySixInverse(+ num 26))))

; Prints any given list
; Source: lisp example file on BB
(defun printList (list)
    (if list
        (progn
            (print (car list))
            (printList (cdr list)))))

; Calculates new index value
(defun getNewIndex (curr_index L)
    (if (= curr_index (- (listLen L) 1))
        0
        (+ curr_index 1)))

; Returns a boolean value indicating
; whether the given character is a
; lowercase letter per ASCII standards
(defun in_range (letter)
    (if (and (< (charToInt letter) 123) (> (charToInt letter) 96))
        t
        nil))

; Returns an encoded message string
(defun encode1 (message key)
    (charListToString (intListToCharList (encode (stringToCharList message) (stringToCharList key) 0))))

; Returns a list of ascii ints
; for an encoded message having
; been given a message char list,
; a key char list, and an index
; that corresponds to the key
(defun encode (message key index)
    (if message
        (if (not (in_range (car message)))
            (cons (charToInt (car message)) (encode (cdr message) key (getNewIndex index key)))
            (cons (twentySix (+ (charToInt (nth index key)) (charToInt (car message)))) (encode (cdr message) key (getNewIndex index key)))))) 

; Returns a decrypted message (of type string)
; having been given an encrypted message and
; corresponding key
(defun decode1 (message key)
    (charListToString (intListToCharList (decode (stringToCharList message) (stringToCharList key) 0))))

; Returns a list of ascii ints
; for a decoded message having
; been given a message char list,
; a key char list, and an index
; that corresponds to the key
(defun decode (message key index)
    (if message
        (if (not (in_range (car message)))
            (cons (charToInt (car message)) (decode (cdr message) key (getNewIndex index key)))
            (cons (twentySixInverse (- (charToInt (car message)) (charToInt (nth index key)))) (decode (cdr message) key (getNewIndex index key)))))) 


(print (encode1 "hello" "abc"))
(print (encode1 "lisp" "common"))
(print (encode1 "This is a test!" "key"))
(print (encode1 "CSCI 530 is the best class ever!" "brownnose"))

(print (decode1 "aygei" "abc"))
(print (decode1 "gpxu" "common"))
(print (decode1 "Tezv zv r qvvq!" "key"))
(print (decode1 "CSCI 530 cc ink mbmd rrgzd yflg!" "brownnose"))