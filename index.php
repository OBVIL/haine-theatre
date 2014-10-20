<?php
ini_set('display_errors', '1');
error_reporting(-1);
include (dirname(__FILE__).'/../teipot/Teipot.php');

// requêter dans la bonne base
$path = Web::pathinfo();
if(strpos($path, 'bibliographie') === 0 || strpos($path, 'seminaire') === 0) $pot = new Teipot(dirname(__FILE__).'/haine-theatre_docs.sqlite', 'fr', $path);
else $pot=new Teipot(dirname(__FILE__).'/haine-theatre.sqlite', 'fr');

$pot->file($pot->path);
$teipot=$pot->basehref.'../teipot/';
$theme=$pot->basehref.'../theme/';
$doc=$pot->doc($pot->path);
if (!isset($doc['body'])) {
  $timeStart=microtime(true);
  $pot->search();
}
?><!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <?php 
if(isset($doc['head'])) echo $doc['head']; 
else echo '
<title>Obvil, haine du théâtre</title>
';
    ?>
    <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900,700italic,600italic' rel='stylesheet' type='text/css' />
    <link rel="stylesheet" type="text/css" href="<?php echo $teipot; ?>html.css" />
    <link rel="stylesheet" type="text/css" href="<?php echo $teipot; ?>teipot.css" />
    <link rel="stylesheet" type="text/css" href="<?php echo $theme; ?>obvil.css" />
    <style>.bibliography h2.head {text-align:left; margin-left: 0;}</style><!-- mise en forme dédiée à la biliographie de F. Lecercle -->
  </head>
  <body>
    <div id="center">
      <header id="header">
        <h1>
          <a href="<?php echo $pot->basehref; ?>">Obvil, haine du théâtre</a>
        </h1>
      </header>
      <div id="contenu">
        <aside id="aside">
          <?php
// les concordances peuvent être très lourdes, placer la nav sans attendre
// livre
if (isset($doc['bookid'])) {
  if(isset($doc['download'])) echo "\n".'<nav id="download">' . $doc['download'] . '</nav>';
  // auteur, titre, date
  echo "\n".'<header>';
  echo "\n".'<div>';
  if (isset($doc['byline'])) echo $doc['byline'];
  if (isset($doc['end'])) echo ' ('.$doc['end'].')';
  echo '</div>';
  echo "\n".'<a class="title" href="'.$pot->basehref.$doc['bookname'].'/">'.$doc['title'].'</a>';
  echo "\n".'</header>';
  // rechercher dans ce livre
  echo '
  <form action=".#conc" name="searchbook" id="searchbook">
    <input name="q" id="q" onclick="this.select()" class="search" size="20" placeholder="Dans ce volume" title="Dans ce volume" value="'. str_replace('"', '&quot;', $pot->q) .'"/>
    <input type="image" id="go" alt="&gt;" value="&gt;" name="go" src="'. $theme . 'img/loupe.png"/>
  </form>
  ';
  // table des matières
  echo '
          <div id="toolpan" class="toc">
            <div class="toc">
              '.$doc['toc'].'
            </div>
          </div>
  ';
}
// accueil ? formulaire de recherche général
else {
  echo'
    <form action="">
      <input name="q" class="text" placeholder="Rechercher" value="'.str_replace('"', '&quot;', $pot->q).'"/>
      <div><label>De <input placeholder="année" name="start" class="year" value="'.$pot->start.'"/></label> <label>à <input class="year" placeholder="année" name="end" value="'. $pot->end .'"/></label></div>
      <button type="reset" onclick="return Form.reset(this.form)">Effacer</button>
      <button type="submit">Rechercher</button>
    </form>
  ';
}
          ?>
        </aside>
        <div id="main">
          <nav id="toolbar">
            <?php
if (isset($doc['prevnext'])) echo $doc['prevnext'];    
            ?>
          </nav>
          <div id="article">
            <?php
if (isset($doc['body'])) {
  echo $doc['body'];
  // page d’accueil d’un livre avec recherche plein texte, afficher une concordance
  if ($pot->q && (!$doc['artname'] || $doc['artname']=='index')) echo $pot->concBook($doc['bookid']);
}
// pas de livre demandé, montrer un rapport général
else {
  // présentation du corpus
  if (!$pot->q) {
    $presentation = new Chtimel('doc/presentation.html');
    echo $presentation->body('');
    }  
  // nombre de résultats
  echo $pot->report();
  // présentation chronologique des résultats
  echo $pot->chrono();
  // présentation bibliographique des résultats
  echo $pot->biblio(array('date', 'byline', 'title', 'occs'));
  // concordance s’il y a recherche plein texte
  echo $pot->concByBook();
}
            ?>
          </div>
        </div>
      </div>
      <?php 
// footer
      ?>
    </div>
    <script type="text/javascript" src="<?php echo $teipot; ?>Tree.js">//</script>
    <script type="text/javascript" src="<?php echo $teipot; ?>Form.js">//</script>
    <script type="text/javascript" src="<?php echo $teipot; ?>Sortable.js">//</script>
    <script type="text/javascript"><?php if (isset($doc['js']))echo $doc['js']; ?></script>  
  </body>
</html>
