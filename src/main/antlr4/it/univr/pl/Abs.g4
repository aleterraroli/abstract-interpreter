grammar Abs;

main : com EOF ;

com : TYPE ID (ASSIGN exp)?             # decl
    | ID ASSIGN exp SC                  # assign
    | IF LPAR exp RPAR com              # if
    | IF LPAR exp RPAR com ELSE com     # ifElse
    | WHILE LPAR exp RPAR com           # while
    | LBRACE com* RBRACE                # block
    | OUT exp SC                        # print
    ;

exp : INT                                # intVal
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

TYPE : 'int' | 'bool' ;
BOOL : 'true' | 'false' ;

INT : [0-9]+ ;

ID : [a-z]+ ;

COMMENT : '//' ~[\r\n]* -> skip ;
WS : [ \t\r\n]+ -> skip ;