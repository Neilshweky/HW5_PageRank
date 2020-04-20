const cheerio = require('cheerio');
const request = require('request-promise');
const fs = require('fs');
var Queue = require('queue-fifo');


const cheerioify = uri => request({ uri, transform: cheerio.load })

const crawl = async function() {
  let q = new Queue();
  q.enqueue('https://www.upenn.edu')
  let i = 0;
  while (!q.isEmpty() && i < 3) {
    await crawlSite(q.dequeue())
    i++;
  }
  
}
const crawlSite = async function(url) {
  cheerioify(url)
    .then($ => {
      $('a').each((i, elem) => {
        console.log($(elem).attr('href'));
      })
    })
    .catch(console.log);
}

// crawl();
crawlSite('https://www.upenn.edu')