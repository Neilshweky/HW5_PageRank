# HW5_PageRank

## Our Team
**Neil Shweky:** nshweky@seas.upenn.edu
**Joan Shaho:** jshaho@seas.upenn.edu

## Our Team
Our idea is to do our own version of PageRank on a much smaller scale. The project involves starting from a “base” website, and searching for any links contained on the webpage. We’ll then record those links on the base webpage as an edge from the base to that link, and repeat this process from all of those links. Once we have the full graph of webpages and outgoing edges, we’ll perform the PageRank algorithm on the graph to find the importance of each webpage in the network. We were also thinking of possibly making the initial website a user input so that the user can explore part of the internet.

We do realize that this graph could get EXTREMELY large, so we’ve decided to cap the number of degrees a website can get from the base website. This will make the graph a manageable size, while also giving us a pretty good representation of the importance of a website within a smaller network.

We are also toying with the idea of only marking a website by its domain name, so that www.a.com/b and www.a.com/c are considered the same website. 
## Project Category
### Information Retrieval
Our project requires the scraping of websites to find the outgoing links of each website. 

### Information Networks
Our project deals with the structure of the World Wide Web, and how websites connect and interact with each other.

### Graph and Graph Algorithms
Our project requires the use of PageRank, which is a graph algorithm which uses a directed graph to determine the importance of a certain node.

## Work Breakdown
We have broken down the work into two parts so far, but we have not decided who will do which part. Both parts seem very manageable for both of us, so it’s not particularly important which of us does what.
1. The scraping of the websites. Finding the outgoing links for each website and recursing on those links. Should output an edge list of the resulting links.
2. The actual PageRank algorithm. Converting the edge list into a usable graph representation, and running the PageRank on this new graph.
