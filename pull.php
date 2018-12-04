<?php
$conf = include( dirname(__FILE__)."/conf.php" );
include( dirname(dirname(__FILE__))."/Teinte/Build.php" );
if (php_sapi_name() == "cli") {
  work($conf);
  exit();
}
?>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Administration, haine du théâtre</title>
    <link rel="stylesheet" type="text/css" href="../Teinte/tei2html.css" />
  </head>
  <body>
    <div id="center" style="padding: 1em; ">
      <h1><a href="pull.php">Administration</a>, <a href="." target="_blank">haine du théâtre</a></h1>
      <form method="POST">
        <label>Mot de passe
          <input name="pass" type="password"/>
        </label>
        <button name="up" type="submit">Mettre à jour</button>
        <label>Forcer
          <input name="force" type="checkbox"/>
        </label>
      </form>
      <section>
      <?php
  if ( !isset( $_POST['pass'] ));
  else if ( $_POST['pass'] != $conf['pass'] ) {
    echo "Mauvais mot de passe";
  }
  else {
    work($conf, isset($_POST['force']));
  }

      ?>
      </section>
    </div>
  </body>
</html>
<?php
function work($conf, $force=null) {
  $getcwd = getcwd();
  chdir($conf['srcdir']);
  echo 'Mise à jour distante <pre style="white-space: pre-wrap;">'."\n";
  // $last = exec( $conf['cmdup'], $output, $ret);
  // echo implode( "\n", $output);
  passthru($conf['cmdup']);
  chdir($getcwd);
  echo '</pre>'."\n";
  // envoyer le csv au build
  echo 'Transformations <pre style="white-space: pre-wrap;">'."\n";
  $build = new Teinte_Build($conf);
  if ($force) $build->clean();
  $build->glob();
  echo '</pre>'."\n";
}
?>
