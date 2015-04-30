dir=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
for f in $dir/input/bounded_sentences/*_stp.txt_bounds.txt; do
    [[ $f == *.xml ]] && continue # skip output files
    g="${f%_stp.txt_bounds.txt}.xml"
    java -mx600m \
         -cp $dir/tools/stanford-ner-2015-04-20/stanford-ner-3.5.2.jar \
         edu.stanford.nlp.ie.crf.CRFClassifier \
         -loadClassifier $dir/tools/stanford-ner-2015-04-20/classifiers/english.all.3class.distsim.crf.ser.gz \
         -textFile "$f" \
         -outputFormat inlineXML > "$g"
done
rm $dir/input/bounded_sentences/*_stp.txt_bounds.txt
