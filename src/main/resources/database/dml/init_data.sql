TRUNCATE users, groups, user_group, snippets RESTART IDENTITY;

INSERT INTO users (firstname, lastname, email, password, user_type)
VALUES ('admin', 'admin', 'admin@epam.com', '21232f297a57a5a743894a0e4a801fc3', 'ADMINISTRATOR'),
       ('Sergei', 'Sergeev', 'serg@mail.ru', '258749fca16faf5a243c14bddaa3afe4', 'SIMPLE_USER'),
       ('Olga', 'Olgina', 'olg_olg@gmail.com', '950f3f950ffdbecac83f234e17a6e876', 'SIMPLE_USER'),
       ('Pavel', 'Pavlov', 'pav_pav@yandex.ru', 'cb8642b77a177cfa23f984a45be6036d', 'SIMPLE_USER'),
       ('Ivan', 'Ivanov', 'ivan@gmail.com', '1552c03e78d38d5005d4ce7b8018addf', 'SIMPLE_USER'),
       ('Mikhail', 'Kolbasov', 'user1@user1', 'b59c67bf196a4758191e42f76670ceba', 'SIMPLE_USER'),
       ('Dmitrii', 'Ananev', 'user2@user2', 'b59c67bf196a4758191e42f76670ceba', 'SIMPLE_USER'),
       ('Aleksandr', 'Gusev', 'user3@user3', 'b59c67bf196a4758191e42f76670ceba', 'SIMPLE_USER'),
       ('Egor', 'Vyrva', 'user4@user4', 'b59c67bf196a4758191e42f76670ceba', 'SIMPLE_USER'),
       ('Pavel', 'Agapov', 'user5@user5', 'b59c67bf196a4758191e42f76670ceba', 'SIMPLE_USER');

INSERT INTO groups (name, description, creator_id)
VALUES ('Common', 'For everyone', 1),
       ('Bioinformatics Fans', 'We love bioinformatics!', 5),
       ('Innovators', 'Our goal is to create something great', 4),
       ('Medical Imaging', 'We create pictures', 6),
       ('Astronomy', 'Cosmic analists', 2),
       ('Physics', 'For cool physics only', 5),
       ('Chemistry', 'We produce chemicals', 3);

INSERT INTO user_group (user_id, group_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1),
       (5, 2),
       (2, 2),
       (2, 5),
       (4, 3),
       (6, 4),
       (7, 5),
       (8, 5),
       (3, 7),
       (4, 7),
       (9, 6);

INSERT INTO snippets (name, owner_id, creation_date, modification_date, content, tag, group_id)
VALUES ('Alleotype.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 2),
       ('Andromeda.cwl', 2, '2019-05-23', '2019-05-23', 'cwlVersion: v1.0
$graph:
- id: compile
  class: CommandLineTool
  inputs:
    src:
      type: File
      inputBinding: {}
    object:
      type: string
      inputBinding:
          prefix: "-o"
  outputs:
    compiled:
      type: File
      outputBinding:
        glob: $(inputs.object)
  baseCommand: gcc
  arguments:
    - "-c"
    - "-Wall"

- id: link
  class: CommandLineTool
  inputs:
    objects:
      type:  File[]
      inputBinding:
        position: 2
    output:
      type: string
      inputBinding:
          position: 1
          prefix: "-o"
  outputs:
    executable:
      type: File
      outputBinding:
          glob: $(inputs.output)
  baseCommand: gcc

- id: main
  class: Workflow
  requirements:
    - class: MultipleInputFeatureRequirement
  inputs: []
  outputs:
    - id: output
      type: File
      outputSource: linkobj/executable
  steps:
    compilesources-src1:
      run: "#compile"
      in:
         src:
           default:
             class: File
             location: source1.c
             secondaryFiles:
               - class: File
                 location: source1.h
         object: { default: "source1.o" }
      out: [compiled]

    compilesources-src2:
      run: "#compile"
      in:
         src: { default: {class: File, location: "source2.c" } }
         object: { default: "source2.o" }
      out: [compiled]

    linkobj:
      run: "#link"
      in:
         objects: [compilesources-src1/compiled, compilesources-src2/compiled]
         output: { default: "a.out" }
      out: [executable]', 'cosmo', 1),
       ('Bonobo.cwl', 2, '2018-12-13', '2019-05-13', '#!/usr/bin/env cwl-runner

cwlVersion: v1.0
class: Workflow

label: "Hello World"
doc: "Outputs a message using echo"

inputs:
  usermessage: string

outputs:
  response:
    outputSource: step0/response
    type: File

steps:
  step0:
    run:
      class: CommandLineTool
      inputs:
        message:
          type: string
          doc: "The message to print"
          default: "Hello World"
          inputBinding:
            position: 1
      baseCommand: echo
      arguments:
         - "-n"
         - "-e"
      stdout: response.txt
      outputs:
        response:
          type: stdout
    in:
      message: usermessage
    out: [response]', 'bio', 2),
       ('Dataflow.cwl', 3, '2018-05-23', '2019-01-15', 'cwlVersion: v1.0

class: CommandLineTool

requirements:
- class: InlineJavascriptRequirement

inputs:
  noise_model:

    type: File
    inputBinding:
      prefix: --noise_model
      valueFrom: |
        ${ return {"path": self.path.match(/(.*)\.stepmodel/)[1], "class": "File"}; }
    secondaryFiles:
    - ^.stuttermodel

    doc: |
      File to read noise model parameters from (.stepmodel)
  bam:
    type: File
    inputBinding:
      prefix: --bam
    secondaryFiles:
    - .bai

    doc: |
      BAM file to analyze. Should have a unique read group and be sorted and indexed.
  reference:

    type: File
    inputBinding:
      prefix: --index-prefix
      valueFrom: |
        ${ return {"path": self.path.match(/(.*)ref\.fasta/)[1], "class": "File"}; }

    secondaryFiles:
    - .amb
    - .ann
    - .bwt
    - .pac
    - .rbwt
    - .rpac
    - .rsa
    - .sa
    - ${return self.basename.replace(/(.*)ref\.fasta/, "$1chromsizes.tab");}
    - ${return self.basename.replace(/(.*)ref\.fasta/, "$1mergedref.bed");}
    - ${return self.basename.replace(/(.*)ref\.fasta/, "$1ref_map.tab");}

    doc: lobSTRs bwa reference files
  strinfo:
    type: File
    inputBinding:
      prefix: --strinfo
    doc: |
      File containing statistics for each STR.
  output_prefix:
    type: string
    inputBinding:
      prefix: --out
    doc: Prefix for output files. will output prefix.vcf and prefix.genotypes.tab
outputs:
  vcf_stats:
    type: File
    outputBinding:
      glob: $(inputs[output_prefix] + .allelotype.stats)
  vcf:
    type: File
    outputBinding:
      glob: $(inputs[output_prefix] + .vcf)
baseCommand: [allelotype, --command, classify]
arguments:
- --noweb
doc: Run lobSTR allelotype classifier.', 'common', 1),
       ('Newpath.cwl', 4, '2018-01-23', '2019-03-03', '#!/usr/bin/env cwl-runner

cwlVersion: v1.0
class: Workflow

label: "Hello World"
doc: "Outputs a message using echo"

inputs:
  usermessage: string

outputs:
  response:
    outputSource: step0/response
    type: File

steps:
  step0:
    run:
      class: CommandLineTool
      inputs:
        message:
          type: string
          doc: "The message to print"
          default: "Hello World"
          inputBinding:
            position: 1
      baseCommand: echo
      arguments:
         - "-n"
         - "-e"
      stdout: response.txt
      outputs:
        response:
          type: stdout
    in:
      message: usermessage
    out: [response]', 'innovative', 3),
       ('Celltransport.cwl', 5, '2018-05-23', '2019-03-03', '#!/usr/bin/env cwl-runner

cwlVersion: v1.0
class: Workflow

label: "Hello World"
doc: "Outputs a message using echo"

inputs:
  usermessage: string

outputs:
  response:
    outputSource: step0/response
    type: File

steps:
  step0:
    run:
      class: CommandLineTool
      inputs:
        message:
          type: string
          doc: "The message to print"
          default: "Hello World"
          inputBinding:
            position: 1
      baseCommand: echo
      arguments:
         - "-n"
         - "-e"
      stdout: response.txt
      outputs:
        response:
          type: stdout
    in:
      message: usermessage
    out: [response]', 'bio', 1),
       ('Apox.cwl', 6, '2000-01-01', '2010-02-01', '#!/usr/bin/env cwl-runner

cwlVersion: v1.0
class: Workflow

label: "Hello World"
doc: "Outputs a message using echo"

inputs:
  usermessage: string

outputs:
  response:
    outputSource: step0/response
    type: File

steps:
  step0:
    run:
      class: CommandLineTool
      inputs:
        message:
          type: string
          doc: "The message to print"
          default: "Hello World"
          inputBinding:
            position: 1
      baseCommand: echo
      arguments:
         - "-n"
         - "-e"
      stdout: response.txt
      outputs:
        response:
          type: stdout
    in:
      message: usermessage
    out: [response]', 'med', 4),
       ('Panacea.cwl', 7, '2000-02-02', '2010-03-02', '#!/usr/bin/env cwl-runner

cwlVersion: v1.0
class: Workflow

label: "Hello World"
doc: "Outputs a message using echo"

inputs:
  usermessage: string

outputs:
  response:
    outputSource: step0/response
    type: File

steps:
  step0:
    run:
      class: CommandLineTool
      inputs:
        message:
          type: string
          doc: "The message to print"
          default: "Hello World"
          inputBinding:
            position: 1
      baseCommand: echo
      arguments:
         - "-n"
         - "-e"
      stdout: response.txt
      outputs:
        response:
          type: stdout
    in:
      message: usermessage
    out: [response]', 'med', 5),
       ('Worlddomination.cwl', 8, '2000-02-02', '2010-04-02', '#!/usr/bin/env cwl-runner

cwlVersion: v1.0
class: Workflow

label: "Hello World"
doc: "Outputs a message using echo"

inputs:
  usermessage: string

outputs:
  response:
    outputSource: step0/response
    type: File

steps:
  step0:
    run:
      class: CommandLineTool
      inputs:
        message:
          type: string
          doc: "The message to print"
          default: "Hello World"
          inputBinding:
            position: 1
      baseCommand: echo
      arguments:
         - "-n"
         - "-e"
      stdout: response.txt
      outputs:
        response:
          type: stdout
    in:
      message: usermessage
    out: [response]', 'innovative', 5),
       ('Blackmatter.cwl', 2, '2019-05-23', '2019-05-23', 'cwlVersion: v1.0
$graph:
- id: compile
  class: CommandLineTool
  inputs:
    src:
      type: File
      inputBinding: {}
    object:
      type: string
      inputBinding:
          prefix: "-o"
  outputs:
    compiled:
      type: File
      outputBinding:
        glob: $(inputs.object)
  baseCommand: gcc
  arguments:
    - "-c"
    - "-Wall"

- id: link
  class: CommandLineTool
  inputs:
    objects:
      type:  File[]
      inputBinding:
        position: 2
    output:
      type: string
      inputBinding:
          position: 1
          prefix: "-o"
  outputs:
    executable:
      type: File
      outputBinding:
          glob: $(inputs.output)
  baseCommand: gcc

- id: main
  class: Workflow
  requirements:
    - class: MultipleInputFeatureRequirement
  inputs: []
  outputs:
    - id: output
      type: File
      outputSource: linkobj/executable
  steps:
    compilesources-src1:
      run: "#compile"
      in:
         src:
           default:
             class: File
             location: source1.c
             secondaryFiles:
               - class: File
                 location: source1.h
         object: { default: "source1.o" }
      out: [compiled]

    compilesources-src2:
      run: "#compile"
      in:
         src: { default: {class: File, location: "source2.c" } }
         object: { default: "source2.o" }
      out: [compiled]

    linkobj:
      run: "#link"
      in:
         objects: [compilesources-src1/compiled, compilesources-src2/compiled]
         output: { default: "a.out" }
      out: [executable]', 'cosmo', 1),
       ('Caffeine.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 1),
       ('Alpha-Centauri.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 1),
       ('Lalande.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'common', 1),
       ('Sirius.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'common', 1),
       ('Proxima-Centauri.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'med', 1),
       ('Luyten.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'innovative', 1),
       ('Epsilon-Eridani.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'innovative', 1),
       ('Lacaille.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 1),
       ('Procyon.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'med', 1),
       ('Nicotine.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 1),
       ('Aldebaran.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'common', 1),
       ('Arcturus.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 1),
       ('Pseudoephedrine.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'cosmo', 1),
       ('Betelgeuse.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 1),
       ('Venus.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'med', 1),
       ('Earth.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 1),
       ('Mars.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'med', 1),
       ('Ceres.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'cosmo', 1),
       ('Jupiter.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 1),
       ('Saturn.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'bio', 1),
       ('Uranus.cwl', 1, '2018-05-23', '2019-03-03', 'cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []', 'cosmo', 1),
       ('magnetars.cwl', 9, '2000-03-03', '2010-05-03', '#!/usr/bin/env cwl-runner

cwlVersion: v1.0
class: Workflow

label: "Hello World"
doc: "Outputs a message using echo"

inputs:
  usermessage: string

outputs:
  response:
    outputSource: step0/response
    type: File

steps:
  step0:
    run:
      class: CommandLineTool
      inputs:
        message:
          type: string
          doc: "The message to print"
          default: "Hello World"
          inputBinding:
            position: 1
      baseCommand: echo
      arguments:
         - "-n"
         - "-e"
      stdout: response.txt
      outputs:
        response:
          type: stdout
    in:
      message: usermessage
    out: [response]', 'cosmo', 6);