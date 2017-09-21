<?php
return array(
  "title" => "Haine du théâtre", // Nom du corpus
  "srcdir" => dirname( __FILE__ ), // dossier source depuis lequel exécuter la commande de mise à jour
  "cmdup" => "git pull", // commande de mise à jour
  "pass" => "", // Mot de passe à renseigner obligatoirement à l’installation
  "srcglob" => array( "xml/*.xml", "xml-diplo/*.xml" ), // sources XML à publier
  "sqlite" => "hdt.sqlite", // nom de la base avec les métadonnées
  "formats" => "article, toc, epub, kindle", // formats générés
);
?>
