package edu.odu.cs.cs350.tm1.output;

%%

%class Lexer
%unicode
%line
%column
%public
%function scan
%type int

%{
    public static final int EOF = 0;
    public static final int WORD = 1;
    public static final int NUMBER = 2;
%}

%eofclose

%%
// Ignore whitespace
[\t\r\n ]+ { /* Ignore */ }

// Token rules
[a-zA-Z]+    { return WORD; }
[0-9]+       { return NUMBER; }
<<EOF>>      { return EOF; }

// Catch-all
.            { return 1; }