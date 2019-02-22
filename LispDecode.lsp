; Gray Meeks
; CSCI 530 Assignment 3 Part 2
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


; encodeFilter returns an int value
; that represents an encoded character
; based on a given message int and
; key int (both representing ascii)
(defun encodeFilter(message_int key_int)
    (if (and (< message_int 123) (> message_int 96))
        (twentySix (+ message_int key_int))
        message_int))

; decodeFilter returns an int value
; that represents a decoded character
; based on a given encrypted message int
; and key int (both representing ascii)
(defun decodeFilter(encrypted_msg_int key_int)
    (if (and (< encrypted_msg_int 123) (> encrypted_msg_int 96))
        (twentySixInverse (- encrypted_msg_int key_int))
        encrypted_msg_int))

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
(defun encode2 (message key)
    (charListToString (intListToCharList (encode (stringToCharList message) (stringToCharList key)))))

; Returns a list of ascii ints
; for an encoded message having
; been given a message char list
; and key char list that are
; both the same length.
(defun encode (message key)
    (mapcar #'encodeFilter (charListToIntList message) (charListToIntList key)))

; Returns a decrypted message (of type string)
; having been given an encrypted message and
; corresponding key of the same length
(defun decode2 (message key)
    (charListToString (intListToCharList (decode (stringToCharList message) (stringToCharList key)))))

; Returns a list of ascii ints
; for a decoded message having
; been given a message char list
; and a key char list that are
; both the same length.
(defun decode (message key)
    (mapcar #'decodeFilter (charListToIntList message) (charListToIntList key)))


(print (encode2 "hello" "abcde"))
(print (encode2 "goodbyes" "sayonara"))
(print (encode2 "functional" "procedural"))
(print (encode2 "Testy Testerson" "blahblahblahbla"))

(print (decode2 "ayghl" "abcde"))
(print (decode2 "rhfkhrol" "sayonara"))
(print (decode2 "neuxqebxtp" "procedural"))
(print (decode2 "Tilts Temxxrmsg" "blahblahblahbla"))
