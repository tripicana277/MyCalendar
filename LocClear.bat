@echo off
:: ログファイルのパスを設定
set LOG_FILE=C:\eclipse\workspace\AppCreate\error.log

:: ログファイルを空にする
type nul > "%LOG_FILE%"