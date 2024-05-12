#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define NODE_NAME_LEN 3

typedef struct Node Node;
struct Node
{
    Node *left;
    Node *right;
    char name[NODE_NAME_LEN + 1];
};

typedef struct NodeList NodeList;
struct NodeList
{
    Node *node;
    NodeList *next;
};

extern Node *start_node;
extern NodeList *node_list_head;

Node *add_node(char *line);
Node *lookup(char *name);
