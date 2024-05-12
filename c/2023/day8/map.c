#include "map.h"

#define LOOKUP_LEN 36
#define NUM_BASE 48
#define LETTER_BASE 55
#define LEFT_OFFSET 7
#define RIGHT_OFFSET 12

NodeList *node_list_head;
NodeList *node_list_tail;
Node *lookup_table[LOOKUP_LEN][LOOKUP_LEN][LOOKUP_LEN];
Node *start_node;

int lookup_value(char c)
{
    if (isdigit(c))
    {
        return c - NUM_BASE;
    }
    return c - LETTER_BASE;
}

Node *lookup(char *name)
{
    int i = lookup_value(name[0]), j = lookup_value(name[1]), k = lookup_value(name[2]);
    Node *node = lookup_table[i][j][k];
    NodeList *node_list;
    if (node == NULL)
    {
        node = malloc(sizeof(Node));
        node_list = malloc(sizeof(NodeList));
        lookup_table[i][j][k] = node;
        node_list->node = node;
        if (node_list_head == NULL)
        {
            node_list_head = node_list_tail = node_list;
        }
        else
        {
            node_list_tail->next = node_list;
            node_list_tail = node_list;
        }
    }
    return node;
}

Node *add_node(char *line)
{
    Node *node = lookup(line);
    node->left = lookup(line + LEFT_OFFSET);
    node->right = lookup(line + RIGHT_OFFSET);
    strncpy(node->name, line, 3);
    if (strcmp(node->name, "AAA") == 0)
    {
        start_node = node;
    }
    return node;
}
