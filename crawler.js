const cheerio = require('cheerio');
const request = require('request-promise');
const fs = require('fs');
var psl = require('psl');
const extractDomain = require('extract-domain');
const validUrl = require('valid-url');

var Queue = require('queue-fifo');

const cheerioify = uri => request({ uri, transform: cheerio.load })
let edgeList = "";

const crawl = async function(url) {
  let q = new Queue();
  q.enqueue(url)
  let visited = new Set();
  visited.add(extractDomain(url));
  let i = 0;
  distances = {}
  distances[url] = 0;
  let max_distance = 0;
  while (!q.isEmpty() && max_distance < 4) {
    let popped = q.peek()
    let set = await crawlSite(q.dequeue())
    set.forEach(elt => {
      let extracted = extractDomain(elt)
      if (!visited.has(extracted)) {
        visited.add(extracted)
        q.enqueue(elt)
        let distance = distances[popped] + 1;
        distances[elt] = distance
        if (distance > max_distance) {
          console.log('new frontier', distance)
          max_distance = distance
        }
      } 
    })
  } 
  writeToFile();
}

const crawlSite = async function(url) {
  const urls = new Set();
  const extracted = new Set();
  return cheerioify(url)
    .then($ => {
      $('a').each((i, elem) => {
        let new_url = $(elem).attr('href')
        if (validUrl.isWebUri(new_url) && url !== new_url) {
          let e_url = extractDomain(new_url)
          if (!extracted.has(e_url) && e_url !== extractDomain(url)) {
            urls.add(new_url);
            if (!!e_url) extracted.add(e_url);
          }
          
        }

      })
      addToEdgeList(extractDomain(url), extracted)
      return urls
    })
    .catch(err => { return new Set() });
}

const addToEdgeList = (url, urls) => edgeList = [...urls].reduce((acc, current) => `${acc}${url}\t${current}\n`, edgeList)

const writeToFile = () => {
  fs.writeFile('edge_list.txt', edgeList, function(err) {
    if(err) console.log(err)
    else console.log('done')
  })
}
// crawl();
crawl('https://www.upenn.edu').then(() => console.log(edgeList))