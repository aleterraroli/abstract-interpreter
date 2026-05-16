grammar Abs;

main : stmt* EOF;

stmt : ID '=' NUMBER ';' ;

ID : [a-zA-Z]+ ;
NUMBER : [0-9]+ ;

WS : [ \t\r\n]+ -> skip ;