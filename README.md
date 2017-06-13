[![Build Status](https://travis-ci.org/dhiegohenrique/meecarros.svg?branch=master)](https://travis-ci.org/dhiegohenrique/meecarros)

Requisitos:
1) Play Framework 2.5 ou superior;
2) Java 8;
3) Bower 1.8 ou superior;

Para testar:
sbt test

Para rodar a aplicação:
bower install
sbt run

A cada commit, serão realizados testes unitários no Travis. Se passarem, o deploy será realizado em http://meecarros.herokuapp.com

A documentação pode ser acessada em http://meecarros.herokuapp.com/docs/