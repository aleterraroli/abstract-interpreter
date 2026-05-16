grammar Abs;

main : stmt* EOF ;

stmt
    : ID ASSIGN exp SC                    # assign
    | IF LPAR exp RPAR stmt              # if
    | IF LPAR exp RPAR stmt ELSE stmt    # ifElse
    | WHILE LPAR exp RPAR stmt           # while
    | LBRACE stmt* RBRACE                # block
    | OUT exp SC                         # print
    ;

exp
    : INT                                # intVal
    | BOOL                               # boolVal
    | ID                                 # id
    | LPAR exp RPAR                      # parExp
    | NOT exp                            # not
    | exp op=(MUL | DIV) exp             # mulDiv
    | exp op=(ADD | SUB) exp             # addSub
    | exp op=(LT | GT | EQQ) exp         # compare
    | exp op=(AND | OR) exp              # logic
    ;

ADD : '+' ;
SUB : '-' ;
MUL : '*' ;
DIV : '/' ;

EQQ : '==' ;
LT  : '<' ;
GT  : '>' ;

NOT : 'not' ;
AND : 'and' ;
OR  : 'or' ;

IF : 'if' ;
ELSE : 'else' ;
WHILE : 'while' ;

ASSIGN : '=' ;
OUT : 'print' ;

LPAR : '(' ;
RPAR : ')' ;

LBRACE : '{' ;
RBRACE : '}' ;

SC : ';' ;

BOOL : 'true' | 'false' ;

INT : [0-9]+ ;

ID : [a-z]+ ;

COMMENT : '//' ~[\r\n]* -> skip ;
WS : [ \t\r\n]+ -> skip ;