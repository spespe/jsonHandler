#!/bin/bash
##PIETRO SPERI

##Launcher
usage(){
	echo -e "\e[33m USAGE: ./launcher.sh [-][OPTIONAL PARAMETERS] \e[0m"
	echo -e "\e[33m OPTION -f : allows to specify the file name to pass as input(JSON format file) \e[0m"
	echo -e "\e[33m OPTION -m : allows to retrieve the members only from the JSON input file specified above \e[0m"
	echo -e "\e[33m OPTION -a : allows to retrieve the arrays only from the JSON input file specified above \e[0m"
	echo -e "\e[33m OPTION -o : allows to retrieve the objects only from the JSON input file specified above \e[0m"
	echo -e "\e[32m EXAMPLE: ./launcher.sh -m /path/to/my/file/fille.json \e[0m"
}

##Parameters
while test $# -gt 0
        do
    case $1 in
                -f|--jsonfile)
                shift
                JSON_FILE=$1
				echo -e "\e[0;33m JSON FILE: $1 \e[0m"
                ;;
                -m|--member)
                shift
                MEMBER=$1
				echo -e "\e[0;33m MEMBER: $1 \e[0m"
                ;;
				-a|--array)
                shift
                ARRAY=$1
				echo -e "\e[0;33m ARRAY: $1 \e[0m"
                ;;
				-o|--object)
                shift
                OBJECT=$1
				echo -e "\e[0;33m OBJECT: $1 \e[0m"
                ;;
                *)
                echo -e "\e[0;31m ERROR. Invalid argument: $1 \e[0m"
                exit 1
                ;;
        esac
        shift
done


