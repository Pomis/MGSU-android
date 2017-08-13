package ru.lodmisis.mgsu.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.lodmisis.mgsu.R;
import ru.lodmisis.mgsu.base.InjectionFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends InjectionFragment {


    View lastOpenedQuestion;

    public FAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Задать вопрос");
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
//        initAdapter();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
    }

    private void bindViews() {
        getActivity().findViewById(R.id.cv_mail).setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ef@mgsu.ru"});
            i.putExtra(Intent.EXTRA_SUBJECT, "");
            i.putExtra(Intent.EXTRA_TEXT   , "");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), "Почтовый клиент не установлен.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void initAdapter() {
//
//        LinearLayout ll = ((LinearLayout) getActivity().findViewById(R.id.ll_faqs));
//        if (ll != null) {
//            ArrayList<CommunityQuestion> questions = new ArrayList<>();
//
//            questions.add(new CommunityQuestion("Что такое эндаумент? ", "Эндаумент-фонд (англ. endowment) – это фонд, формируемый за счет благотворительных вкладов выпускников и партнеров МИСиС. Денежные средства инвестируются в ценные бумаги, а доход от инвестирования ежегодно направляется на развитие университета и обеспечивает финансовую стабильность вуза.\n" +
//                    "Как деятельность эндаумент-фондов регулируется в России? \n" +
//                    "Создание эндаумент-фондов в России стало возможным с принятием Федерального закона от 30.12.2006 г. № 275-ФЗ «О порядке формирования и использования целевого капитала некоммерческих организаций».\n" +
//                    "Образование и наука были определены в данном законе (наряду с такими сферами, как здравоохранение, спорт, культура, искусство и др.) как области, где возможно формирование целевого капитала и использование доходов от него (п. 1 ст. 3 Закона о целевом капитале)."));
//            questions.add(new CommunityQuestion("Что такое целевой капитал? ",
//                    "Целевой капитал – это механизм для создания эндаумент-фондов в соответствии с российским законодательством. \n" +
//                            "Целевой капитал – это часть имущества некоммерческой организации, которая формируется и пополняется за счет пожертвований и передается некоммерческой организацией в доверительное управление управляющей компании в целях получения дохода, используемого для финансирования уставной деятельности такой некоммерческой организации или иных некоммерческих организаций (подробнее – см. п.1 ст. 2 Закона о целевом капитале)."));
//            questions.add(new CommunityQuestion("Могут ли государственные учреждения быть собственниками целевого капитала? ",
//                    "Государственные учреждения (к которым относится и НИТУ «МИСиС») не могут являться собственниками целевого капитала, так как некоммерческая организация – собственник целевого капитала может быть создана только в форме фонда, автономной некоммерческой организации, общественной организации, общественного фонда или религиозной организации (п. 4 ст. 2 Закона о целевом капитале).\n" +
//                            "В этой связи схема финансирования государственных и муниципальных учреждений за счет целевого капитала предполагает создание специализированной некоммерческой организации управления целевым капиталом в организационно-правовой форме фонда (фонд или фонд целевого капитала), которая выступает собственником целевого капитала. Для НИТУ «МИСиС» был создан Эндаумент-фонд НИТУ «МИСиС» (Специализированный фонд формирования целевого капитала НИТУ «МИСиС»)."));
//            questions.add(new CommunityQuestion("Каковы цели деятельности фонда целевого капитала? ", "Эндаумент-фонд создается исключительно для формирования целевого капитала, использования, распределения дохода от него в пользу иных получателей дохода от целевого капитала. Эндаумент формирует один или несколько целевых капиталов и передает их управляющей компании в доверительное управление, получает доходы от доверительного управления целевым капиталом и направляет (распределяет) эти доходы конкретным учреждениям, являющимся конечными получателями доходов от целевого капитала. Получателем дохода от целевого капитала, созданного Эндаумент-фондом МИСиС, является НИТУ «МИСиС»."));
//            questions.add(new CommunityQuestion("Как используется целевой капитал? ", "Порядок использования целевого капитала определяется в Уставе фонда и его финансовом плане, который формируется с учетом требований договоров пожертвований (завещаний) (ст. 13 Закона о целевом капитале).\n" +
//                    "Нормативно-правовая база, регламентирующая порядок формирования и использования целевого капитала некоммерческих организаций:\n" +
//                    "Федеральный закон от 30.12.2006 г. № 275-ФЗ «О порядке формирования и использования целевого капитала некоммерческих организаций»\n" +
//                    "Федеральный закон от 12.01.1996 г. № 7-ФЗ «О некоммерческих организациях».\n" +
//                    "Гражданский кодекс Российской Федерации (часть первая) от 30.11.1994 г. № 51-ФЗ \n" +
//                    "Гражданский кодекс Российской Федерации (часть вторая) от 26.01.1996 г. № 14-ФЗ\n" +
//                    "Налоговый кодекс Российской Федерации (часть вторая) от 05.08.2000 г. № 117-ФЗ\n" +
//                    "Приказ ФСФР РФ от 14.06.07 № 07-67/пз-н «Об утверждении правил расчета стоимости чистых активов, находящихся в доверительном управлении управляющей компании по договору доверительного управления имуществом, составляющим целевой капитал»."));
//
//            QuestionAdapter adapter = new QuestionAdapter(getActivity(), 0, questions);
//            int counter = 0;
//            for (CommunityQuestion question : questions) {
//                View view = adapter.getView(counter++, null, ll);
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (v.findViewById(R.id.tv_row_hideable).getVisibility() == View.VISIBLE) {
//                            v.findViewById(R.id.tv_row_hideable).setVisibility(View.GONE);
//                            lastOpenedQuestion = null;
//                        } else {
//                            if (lastOpenedQuestion!=null){
//                                lastOpenedQuestion.findViewById(R.id.tv_row_hideable).setVisibility(View.GONE);
//                            }
//                            v.findViewById(R.id.tv_row_hideable).setVisibility(View.VISIBLE);
//                            lastOpenedQuestion = v;
//                        }
//                    }
//                });
//                ll.addView(view);
//
//            }
//        }
//    }


}
